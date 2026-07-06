package com.oopsw.kostaerpserver.auth.filter;

import com.oopsw.kostaerpserver.auth.support.JwtProvider;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import java.io.IOException;
import java.util.List;

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
            Claims claims = jwtProvider.parseClaims(token); //서명, 만료 검증(실패 시 예외)
            String username = claims.getSubject();
            String role = claims.get("role", String.class);

            //UPA토큰 생성
            UsernamePasswordAuthenticationToken authentication =
                    new UsernamePasswordAuthenticationToken(
                            username,
                        null, List.of(new SimpleGrantedAuthority(role)));

            //UPA토큰 기반 ContextHolder 생성
            SecurityContextHolder.getContext().setAuthentication(authentication);

        } catch (ExpiredJwtException e) {
            SecurityContextHolder.clearContext();
            response.setHeader("Token-Error", "expired");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        } catch (Exception e) {
            SecurityContextHolder.clearContext();
            response.setHeader("Token-Error", "invalid");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        }

        //다음 필터로
        chain.doFilter(request, response);
    }
}
