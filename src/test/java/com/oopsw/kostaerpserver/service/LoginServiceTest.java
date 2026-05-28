package com.oopsw.kostaerpserver.service;


import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@Slf4j
@ActiveProfiles("test")
@SpringBootTest
public class LoginServiceTest {
    @Autowired
    LoginServiceImpl loginService;


    @Test
    void loginTest() {
        log.info("text");
    }


}
