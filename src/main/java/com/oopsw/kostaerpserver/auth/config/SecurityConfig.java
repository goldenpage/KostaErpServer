package com.oopsw.kostaerpserver.auth.config;

import com.oopsw.kostaerpserver.auth.ErpUserDetailsService;
import com.oopsw.kostaerpserver.auth.JsonLoginFilter;
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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final ErpUserDetailsService erpUserDetailsService;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http,
        AuthenticationManager authenticationManager) throws Exception {
        JsonLoginFilter jsonLoginFilter = new JsonLoginFilter(
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
                }));
        http.csrf(csrf -> csrf.disable());

        http.authorizeHttpRequests(auth -> auth
            .requestMatchers(
                "/login",
                "/register",
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
        );
        http.addFilterAt(jsonLoginFilter,
            UsernamePasswordAuthenticationFilter.class);

        http.logout(logout ->
            logout.logoutUrl("/logout")
                .logoutSuccessUrl("/login")
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")
            );
        return http.build();
    }


    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(erpUserDetailsService);
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
}
