package com.oopsw.kostaerpserver.service.Interface;

public interface PhoneVerificationCodeSenderService {

    void send(String phone, String code);
}
