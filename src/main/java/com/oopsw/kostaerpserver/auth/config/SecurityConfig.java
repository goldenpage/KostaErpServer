package com.oopsw.kostaerpserver.auth.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.oopsw.kostaerpserver.auth.filter.JwtAuthenticationFilter;
import com.oopsw.kostaerpserver.auth.filter.JwtAuthorizationFilter;
import com.oopsw.kostaerpserver.auth.service.AuthService;
import com.oopsw.kostaerpserver.auth.support.JwtProvider;
import com.oopsw.kostaerpserver.auth.userdetails.AccountDetailsService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.filter.CorsFilter;

@Slf4j
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final CorsFilter corsFilter;
    private final JwtProvider jwtProvider;
    private final AuthService authService;
    private final ObjectMapper objectMapper;
    private final AccountDetailsService accountDetailsService;

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(accountDetailsService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration configuration
    ) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, AuthenticationManager authenticationManager) throws Exception {

        http
            .csrf(csrf -> csrf.disable())
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .requestCache(cache -> cache.disable())  //인증되지 않은 사용자 보호 url 접근시 쿠키생성 비활성화
            .formLogin(form -> form.disable())
            .httpBasic(basic -> basic.disable())
            .logout(logout -> logout.disable())

            .addFilter(corsFilter)
            .addFilter(new JwtAuthenticationFilter(authenticationManager, jwtProvider, authService, objectMapper))
            .addFilter(new JwtAuthorizationFilter(authenticationManager, jwtProvider))

            .authorizeHttpRequests(auth -> auth
                .requestMatchers(
                     "/login",
                     "/register",
                     "/api/auth/reissue",
                     "/api/auth/logout",
                     "/api/auth/login",
                     "/api/auth/register",
                     "/api/auth/phone/**",
                     "/api/sales/**",
                     "/css/**",
                     "/js/**",
                     "/asset/**",
                     "/swagger-ui/**",
                     "/swagger-ui.html",
                     "/v3/api-docs/**"
                 ).permitAll()
                    .requestMatchers("/api/manager/**").hasRole("MANAGER")
                    .requestMatchers("/manager/**").hasRole("MANAGER")
                    .anyRequest().authenticated()
                )
                .exceptionHandling(ex -> ex.authenticationEntryPoint(
                        (request, response, authException) -> {
                            log.error("[SecurityConfig] .exceptionHandling : " + authException.getMessage());
                            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                            response.getWriter().write("{\"message\":\"authentication required\"}");
                        }));

        return http.build();
    }
}
