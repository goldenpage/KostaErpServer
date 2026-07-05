package com.oopsw.kostaerpserver.auth.service;

import com.oopsw.kostaerpserver.auth.dto.TokenResponse;
import com.oopsw.kostaerpserver.auth.repository.AccountRepository;
import com.oopsw.kostaerpserver.auth.repository.RefreshTokenRepository;
import com.oopsw.kostaerpserver.auth.repository.entity.Account;
import com.oopsw.kostaerpserver.auth.repository.entity.RefreshToken;
import com.oopsw.kostaerpserver.auth.support.JwtProvider;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class AuthService {
    private final JwtProvider jwtProvider;
    private final RefreshTokenRepository refreshTokenRepository;
    private final AccountRepository accountRepository;

    public void createUserAccount(String username, String password, String name, String email) {
        if(username == null || accountRepository.existsByUsername(username)) {
            return; //이미 있으면 스킵
        }
        accountRepository.save(Account.builder()
                .username(username)
                .password(password)
                .role("ROLE_USER")
                .email(email)
                .name(name)
                .build());
    }

    //AT, RT 생성 및 RT DB저장
    @Transactional
    public TokenResponse issueToken(String username, String role) {
        String accesssToken = jwtProvider.createAccessToken(username, role);
        String refreshToken = jwtProvider.createRefreshToken(username);
        long expiryDate = System.currentTimeMillis() + jwtProvider.getRefreshExpMillis();

        saveRT(username, refreshToken, expiryDate);

        return TokenResponse.builder()
                .accessToken(accesssToken)
                .refreshToken(refreshToken)
                .build();
    }

    //재발급
    @Transactional
    public TokenResponse reissue(String refreshToken) {
        if(refreshToken == null) throw new JwtException("RT 없음");

        String username;
        try {
            username = jwtProvider.getUsername(refreshToken);
        } catch (Exception e) {
            throw new JwtException("RT 유효하지 않음");
        }

        RefreshToken saved = refreshTokenRepository.findByUsername(username)
                .orElseThrow(() -> new JwtException("저장된 RT 없음(로그아웃 상태)"));

        //쿠키 RT 와 DB RT 서로 다름 탈취 되었거나 구버전 -> 삭제하고 거부
        if(!saved.getToken().equals(refreshToken)) {
            refreshTokenRepository.deleteByUsername(username);
            throw new JwtException("RT 불일치 (탈취 의심)");
        }

        //DB 만료 체크
        if(saved.getExpiryDate() < System.currentTimeMillis()) {
            refreshTokenRepository.deleteByUsername(username);
            throw new JwtException("RT 만료");
        }

        Account account = accountRepository.findByUsername(username); //모든 검증 끝 -> 사용자 정보 가져옴

        if (account == null) {                                 //그 사이 계정 삭제/비활성
            refreshTokenRepository.deleteByUsername(username);  //고아 RT도 정리
            throw new JwtException("계정 없음(삭제/비활성)");
        }

        return issueToken(username, account.getRole()); //AT, RT 생성 리턴
    }

    @Transactional
    public void logout(String refreshToken) {
        if(refreshToken == null || refreshToken.isBlank()) return;

        String username;
        try {
            username = jwtProvider.getUsername(refreshToken);
        } catch (ExpiredJwtException e) {
            username = e.getClaims().getSubject(); //만료되었을때도 RT 삭제
        } catch (JwtException e) {
            return;
        }
        refreshTokenRepository.deleteByUsername(username); // 서버측 무효화
    }

    //RT DB저장
    @Transactional
    public void saveRT(String username, String refreshToken, long expiryDate) {
        refreshTokenRepository.save(
                refreshTokenRepository.findByUsername(username)
                        .map(rt -> {rt.setToken(refreshToken); rt.setExpiryDate(expiryDate); return rt;})
                        .orElseGet(() -> RefreshToken.builder()
                                .username(username)
                                .token(refreshToken)
                                .expiryDate(expiryDate)
                                .build())
        );
    }
}
