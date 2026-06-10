package com.oopsw.kostaerpserver.service;

import com.oopsw.kostaerpserver.repository.dao.UserInfoDAO;
import com.oopsw.kostaerpserver.service.Interface.PhoneVerificationCodeSender;
import jakarta.servlet.http.HttpSession;
import java.security.SecureRandom;
import java.time.Duration;
import java.time.Instant;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PhoneVerificationService {

    private static final String PHONE_ATTRIBUTE = "phoneVerification.phone";
    private static final String CODE_ATTRIBUTE = "phoneVerification.code";
    private static final String EXPIRES_AT_ATTRIBUTE = "phoneVerification.expiresAt";
    private static final String ATTEMPTS_ATTRIBUTE = "phoneVerification.attempts";
    private static final String VERIFIED_ATTRIBUTE = "phoneVerification.verified";
    private static final String VERIFIED_AT_ATTRIBUTE = "phoneVerification.verifiedAt";
    private static final String LAST_SENT_AT_ATTRIBUTE = "phoneVerification.lastSentAt";
    private static final Duration CODE_TTL = Duration.ofMinutes(5);
    private static final Duration VERIFIED_TTL = Duration.ofMinutes(10);
    private static final Duration RESEND_COOLDOWN = Duration.ofSeconds(30);
    private static final int MAX_ATTEMPTS = 5;

    private final UserInfoDAO userInfoDAO;
    private final PhoneVerificationCodeSender codeSender;
    private final SecureRandom secureRandom = new SecureRandom();

    public void sendCode(String phone, HttpSession session) {
        String normalizedPhone = normalizePhone(phone);
        if (userInfoDAO.getPhoneCheck(normalizedPhone) > 0) {
            throw new IllegalArgumentException("이미 가입된 휴대폰 번호입니다.");
        }
        Instant lastSentAt = (Instant) session.getAttribute(LAST_SENT_AT_ATTRIBUTE);
        if (lastSentAt != null && Instant.now().isBefore(lastSentAt.plus(RESEND_COOLDOWN))) {
            throw new IllegalArgumentException("인증번호는 30초 후에 다시 발송할 수 있습니다.");
        }

        String code = String.format("%06d", secureRandom.nextInt(1_000_000));
        session.setAttribute(PHONE_ATTRIBUTE, normalizedPhone);
        session.setAttribute(CODE_ATTRIBUTE, code);
        session.setAttribute(EXPIRES_AT_ATTRIBUTE, Instant.now().plus(CODE_TTL));
        session.setAttribute(ATTEMPTS_ATTRIBUTE, 0);
        session.setAttribute(VERIFIED_ATTRIBUTE, false);
        session.removeAttribute(VERIFIED_AT_ATTRIBUTE);
        session.setAttribute(LAST_SENT_AT_ATTRIBUTE, Instant.now());

        codeSender.send(normalizedPhone, code);
    }

    public void verify(String phone, String code, HttpSession session) {
        String normalizedPhone = normalizePhone(phone);
        if (code == null || !code.matches("\\d{6}")) {
            throw new IllegalArgumentException("6자리 인증번호를 입력해주세요.");
        }

        String sentPhone = (String) session.getAttribute(PHONE_ATTRIBUTE);
        String sentCode = (String) session.getAttribute(CODE_ATTRIBUTE);
        Instant expiresAt = (Instant) session.getAttribute(EXPIRES_AT_ATTRIBUTE);
        int attempts = getAttempts(session);

        if (sentPhone == null || sentCode == null || expiresAt == null) {
            throw new IllegalArgumentException("인증번호를 먼저 발송해주세요.");
        }
        if (!sentPhone.equals(normalizedPhone)) {
            clear(session);
            throw new IllegalArgumentException("인증번호를 발송한 휴대폰 번호와 일치하지 않습니다.");
        }
        if (Instant.now().isAfter(expiresAt)) {
            clear(session);
            throw new IllegalArgumentException("인증번호가 만료되었습니다. 다시 발송해주세요.");
        }
        if (attempts >= MAX_ATTEMPTS) {
            clear(session);
            throw new IllegalArgumentException("인증 시도 횟수를 초과했습니다. 다시 발송해주세요.");
        }
        if (!sentCode.equals(code)) {
            session.setAttribute(ATTEMPTS_ATTRIBUTE, attempts + 1);
            throw new IllegalArgumentException("인증번호가 일치하지 않습니다.");
        }

        session.setAttribute(VERIFIED_ATTRIBUTE, true);
        session.setAttribute(VERIFIED_AT_ATTRIBUTE, Instant.now());
        session.removeAttribute(CODE_ATTRIBUTE);
        session.removeAttribute(EXPIRES_AT_ATTRIBUTE);
        session.removeAttribute(ATTEMPTS_ATTRIBUTE);
    }

    public String requireVerified(String phone, HttpSession session) {
        String normalizedPhone = normalizePhone(phone);
        String verifiedPhone = (String) session.getAttribute(PHONE_ATTRIBUTE);
        Instant verifiedAt = (Instant) session.getAttribute(VERIFIED_AT_ATTRIBUTE);
        boolean verified = Boolean.TRUE.equals(session.getAttribute(VERIFIED_ATTRIBUTE));

        if (!verified
            || verifiedAt == null
            || Instant.now().isAfter(verifiedAt.plus(VERIFIED_TTL))
            || !normalizedPhone.equals(verifiedPhone)) {
            clear(session);
            throw new IllegalArgumentException("휴대폰 인증을 완료해주세요.");
        }
        return normalizedPhone;
    }

    public void clear(HttpSession session) {
        session.removeAttribute(PHONE_ATTRIBUTE);
        session.removeAttribute(CODE_ATTRIBUTE);
        session.removeAttribute(EXPIRES_AT_ATTRIBUTE);
        session.removeAttribute(ATTEMPTS_ATTRIBUTE);
        session.removeAttribute(VERIFIED_ATTRIBUTE);
        session.removeAttribute(VERIFIED_AT_ATTRIBUTE);
        session.removeAttribute(LAST_SENT_AT_ATTRIBUTE);
    }

    private int getAttempts(HttpSession session) {
        Object attempts = session.getAttribute(ATTEMPTS_ATTRIBUTE);
        return attempts instanceof Integer value ? value : 0;
    }

    private String normalizePhone(String phone) {
        if (phone == null || phone.isBlank()) {
            throw new IllegalArgumentException("휴대폰 번호는 필수입니다.");
        }

        String normalized = phone.replaceAll("\\D", "");
        if (!normalized.matches("010\\d{8}")) {
            throw new IllegalArgumentException("휴대폰 번호 형식이 올바르지 않습니다.");
        }
        return normalized;
    }
}
