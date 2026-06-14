package com.oopsw.kostaerpserver.dto.auth;

public record PhoneVerificationRequest(
    String phone,
    String code
) {
}
