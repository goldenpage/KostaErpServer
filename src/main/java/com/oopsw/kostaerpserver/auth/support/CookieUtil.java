package com.oopsw.kostaerpserver.auth.support;

import org.springframework.http.ResponseCookie;

import java.time.Duration;

public class CookieUtil {
    //RT 쿠키정보 설정
    public static ResponseCookie refreshCookie(String token, long maxAgeMillis) {
        return ResponseCookie.from("refreshToken", token)
                .httpOnly(true)
                .secure(false)
                .path("/api/auth")
                .maxAge(Duration.ofMillis(maxAgeMillis))
                .sameSite("Lax")
                .build();
    }

    //RT 쿠키정보 삭제 설정
    public static ResponseCookie deleteRefreshCookie() {
        return ResponseCookie.from("refreshToken", "")
                .httpOnly(true)
                .secure(false)
                .path("/api/auth")
                .maxAge(0)
                .sameSite("Lax")
                .build();
    }

}
