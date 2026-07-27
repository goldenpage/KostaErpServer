package com.oopsw.kostaerpserver.auth.config;


import com.oopsw.kostaerpserver.auth.support.JwtProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.List;

@Configuration
public class CorsConfig {
    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true); //쿠키 주고받기
        config.addAllowedOriginPattern("*"); //개발용 전체 허용
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");
        config.setExposedHeaders(List.of(JwtProvider.HEADER, "Token-Status")); //AT 헤더 JS 노출 (RT 쿠키는 자동)
        source.registerCorsConfiguration("/**", config);

        return new CorsFilter(source);
    }
}
