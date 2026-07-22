package com.oopsw.kostaerpserver.auth.filter;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.oopsw.kostaerpserver.auth.repository.entity.Account;
import com.oopsw.kostaerpserver.auth.support.JwtProvider;
import com.oopsw.kostaerpserver.auth.userdetails.AccountDetails;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import java.io.IOException;
import java.util.List;

@Slf4j
public class JwtAuthorizationFilter extends BasicAuthenticationFilter {
    private final JwtProvider jwtProvider;

    public JwtAuthorizationFilter(AuthenticationManager authenticationManager, JwtProvider jwtProvider) {
        super(authenticationManager);
        this.jwtProvider = jwtProvider;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        String header = request.getHeader(JwtProvider.HEADER);

        if(header == null || !header.startsWith(JwtProvider.PREFIX)){
            chain.doFilter(request, response);
            return;
        }

        //Bearer 뒤에 문자열
        String token = header.substring(JwtProvider.PREFIX.length());

        try {
            DecodedJWT decodedToken = jwtProvider.verify(token); //서명, 만료 검증(실패 시 예외)
            String username = decodedToken.getSubject();
            String role = decodedToken.getClaim("role").asString();
            Account account = new  Account();
            account.setUsername(username);
            account.setRole(role);


            //UPA토큰 생성
            UsernamePasswordAuthenticationToken authentication =
                    new UsernamePasswordAuthenticationToken(
                            new AccountDetails(account), null, List.of(new SimpleGrantedAuthority(role)));

            //UPA토큰 기반 ContextHolder 생성
            SecurityContextHolder.getContext().setAuthentication(authentication);

        } catch (TokenExpiredException e) {
            SecurityContextHolder.clearContext();
            response.setHeader("Token-Status", "expired");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            log.error("[JwtAuthorizationFilter] TokenExpiredException : 토큰만료");
        } catch (JWTVerificationException e) {
            SecurityContextHolder.clearContext();
            response.setHeader("Token-Status", "invalid");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            log.error("[JwtAuthorizationFilter] JWTVerificationException : 토큰검증X");
        }

        //다음 필터로
        chain.doFilter(request, response);
    }
}
