package com.oopsw.kostaerpserver.service.Interface;

public interface PhoneVerificationCodeSender {

    void send(String phone, String code);
}
