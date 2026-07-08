package com.oopsw.kostaerpserver.auth.support;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.Date;

@Slf4j
@Component
public class JwtProvider {
    public static final String HEADER = "Authorization";
    public static final String PREFIX = "Bearer ";

    //private final SecretKey key;
    private final Algorithm algorithm;
    private final long accessExpMillis;
    private final long refreshExpMillis;

    public JwtProvider(
            @Value("${jwt.secret}") String secret,
            @Value("${jwt.access-expiration}")Duration accessExp,
            @Value("${jwt.refresh-expiration}")Duration refreshExp) {
        //this.key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
        this.algorithm = Algorithm.HMAC256(secret.getBytes(StandardCharsets.UTF_8));
        this.accessExpMillis = accessExp.toMillis();
        this.refreshExpMillis = refreshExp.toMillis();
    }

    public String createAccessToken(String username, String role) {
        long now = System.currentTimeMillis();
        /*return Jwts.builder()
                .subject(username)
                .claim("role", role)
                .issuedAt(new Date(now))
                .expiration(new Date(now + accessExpMillis))
                .signWith(key)
                .compact();*/
        log.info("[JwtProvider] createAccessToken : username={}, role={}", username, role);
        return JWT.create()
                .withSubject(username)
                .withClaim("role", role)
                .withIssuedAt(new Date(now))
                .withExpiresAt(new Date(now + accessExpMillis))
                .sign(algorithm);
    }

    public String createRefreshToken(String username) {
        long now = System.currentTimeMillis();
       /* return Jwts.builder()
                .subject(username)
                .issuedAt(new Date(now))
                .expiration(new Date(now + refreshExpMillis))
                .signWith(key)
                .compact();*/
        log.info("[JwtProvider] createRefreshToken : username={}", username);
        return JWT.create()
                .withSubject(username)
                .withIssuedAt(new Date(now))
                .withExpiresAt(new Date(now + refreshExpMillis))
                .sign(algorithm);
    }

    /*public Claims parseClaims(String token) {
        return Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }*/
    public DecodedJWT verify(String token) {
        log.info("[JwtProvider] verify : {} 토큰검증 시작", token);
        return JWT.require(algorithm)
                .build()
                .verify(token);
    }

    public String getUsername(String token) {
        /*return parseClaims(token).getSubject();*/
        return verify(token).getSubject();
    }

    public long getRefreshExpMillis() {
        return refreshExpMillis;
    }
}
