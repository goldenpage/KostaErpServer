package com.oopsw.kostaerpserver.service.Interface;

import jakarta.servlet.http.HttpSession;

public interface PhoneVerificationService {

    void sendCode(String phone, HttpSession session);

    void verify(String phone, String code, HttpSession session);

     String requireVerified(String phone, HttpSession session);

     void clear(HttpSession session);
}
