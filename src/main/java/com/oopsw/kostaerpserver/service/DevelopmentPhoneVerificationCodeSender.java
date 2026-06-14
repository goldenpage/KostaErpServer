package com.oopsw.kostaerpserver.service;

import com.oopsw.kostaerpserver.service.Interface.PhoneVerificationCodeSenderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class DevelopmentPhoneVerificationCodeSender implements
    PhoneVerificationCodeSenderService {

    @Override
    public void send(String phone, String code) {
        log.info("개발용 회원가입 휴대폰 인증번호 phone={}, code={}", phone, code);
    }
}
