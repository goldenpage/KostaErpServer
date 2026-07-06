package com.oopsw.kostaerpserver.auth.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.oopsw.kostaerpserver.auth.filter.JwtAuthenticationFilter;
import com.oopsw.kostaerpserver.auth.filter.JwtAuthorizationFilter;
import com.oopsw.kostaerpserver.auth.service.AuthService;
import com.oopsw.kostaerpserver.auth.support.JwtProvider;
import com.oopsw.kostaerpserver.auth.userdetails.AccountDetailsService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
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
   /*     JsonLoginFilter jsonLoginFilter = new JsonLoginFilter(
            authenticationManager);

        jsonLoginFilter.setSecurityContextRepository(new HttpSessionSecurityContextRepository());

        jsonLoginFilter.setAuthenticationSuccessHandler((req, res, auth) -> {
            boolean isManager =
                auth.getAuthorities().stream().anyMatch(
                    authority -> authority.getAuthority()
                        .equals("ROLE_MANAGER"));

            String redirectUrl = isManager ? "/manager" : "/foodmaterials";

            res.setStatus(HttpServletResponse.SC_OK);
            res.setContentType("application/json;charset=UTF-8");
            res.getWriter().write("{\"message\":\"login success\", "
                + "\"redirectUrl\":\"" + redirectUrl + "\"}");
        });

        jsonLoginFilter.setAuthenticationFailureHandler((req, res, ex) -> {
            res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            res.setContentType("application/json;charset=UTF-8");
            res.getWriter().write("{\"message\":\"login fail\"}");
        });

        http.exceptionHandling(exception ->
            exception.authenticationEntryPoint(
                (request, response, authException) -> {
                    if (request.getRequestURI().startsWith("/api/")) {
                        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                        response.getWriter()
                            .write("{\"message\":\"authentication required\"}");
                        return;
                    }
                    response.sendRedirect("/login");
                }));*/

        http
            .csrf(csrf -> csrf.disable())
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
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
                            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                            response.getWriter().write("{\"message\":\"authentication required\"}");
                            System.out.println(authException);
                        }));

        return http.build();
    }
}
