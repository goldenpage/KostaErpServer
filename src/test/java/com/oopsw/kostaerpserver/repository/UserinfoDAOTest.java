package com.oopsw.kostaerpserver.repository;

import com.oopsw.kostaerpserver.vo.User;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import org.springframework.test.context.ActiveProfiles;

@Slf4j
@ActiveProfiles("test")
@SpringBootTest
public class UserinfoDAOTest {

    @Autowired
    UserInfoDAO userInfoDAO;

    private User testUser() {
        return User.builder()
            .bId("test001")
            .pw("1234")
            .phone("01012345678")
            .name("테스트유저")
            .email("test@example.com")
            .storeName("테스트가게")
            .storeType("음식점")
            .storeCategory("한식")
            .marketingDate(new Date())
            .build();
    }

    @Test
    void loginTest() {
        userInfoDAO.login("0000000000","test123!");
        log.info("login result={}",  userInfoDAO.login("0000000000","kim123!"));
    }


    @Test
    void register() {
        log.info("register result={}", userInfoDAO.register(testUser()));
    }

    @Test
    void checkMemberByVO() {
        log.info("checkMemberByVO result={}", userInfoDAO.checkMemberByVO(testUser()));
    }

    @Test
    void setPw() {
        User user = testUser();
        user.setPw("5678");
        log.info("setPw result={}", userInfoDAO.setPw(user));
    }

    @Test
    void getPhoneCheck() {
        log.info("getPhoneCheck result={}", userInfoDAO.getPhoneCheck("01012345678"));
    }

    @Test
    void getBidCheck() {
        log.info("getBidCheck result={}", userInfoDAO.getBidCheck("test001"));
    }

    @Test
    void checkPwFindUser() {
        log.info("checkPwFindUser result={}", userInfoDAO.checkPwFindUser(testUser()));
    }
}