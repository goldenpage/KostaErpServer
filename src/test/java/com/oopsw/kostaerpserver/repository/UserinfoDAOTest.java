package com.oopsw.kostaerpserver.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.oopsw.kostaerpserver.vo.User;
import java.time.LocalDateTime;
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


    @Test
    void loginTest() {
         User user  = userInfoDAO.login("0000000000","kim123!");
        assertEquals("0000000000", user.getBId());

        //log.info("login result={}",  userInfoDAO.login("0000000000","kim123!"));
    }


    @Test
    void register() {
        User request = User.builder()
            .bId("0000000003")
            .name("김테스트")
            .phone("010-1111-2222")
            .email("test@test.com")
            .storeName("테스트가게")
            .storeType("음식점")
            .storeCategory("한식")
            .pw("kim1123!")
            .signDate(LocalDateTime.now())
            .agreementDate(LocalDateTime.now())
            .marketingDate(LocalDateTime.now())
            .build();

        int result = userInfoDAO.register(request);

        assertEquals(1, result);
    }

    @Test
    void checkMemberByVO() {
        User user = userInfoDAO.checkMemberByVO("0000000000", "김사장","kim123!");
        assertEquals("0000000000", user.getBId());
        //log.info("checkMemberByVO result={}", userInfoDAO.checkMemberByVO(testUser()));
    }

    @Test
    void setPw() {
        assertTrue(userInfoDAO.setPw("test123", "0000000000", "김사장",
            "01000000000")==1);

        //log.info("setPw result={}", userInfoDAO.setPw(user));
    }

    @Test
    void getPhoneCheck() {
        int phone = userInfoDAO.getPhoneCheck("01000000000");
        //log.info(userInfoDAO.getPhoneCheck("000000000"));
        assertEquals(1, phone);
    }
        //log.info("getPhoneCheck result={}", userInfoDAO.getPhoneCheck("01012345678"));

    @Test
    void getBidCheck() {
        int id = userInfoDAO.getBidCheck("0000000000");
        assertEquals(1,id);
        //log.info("getBidCheck result={}", userInfoDAO.getBidCheck("test001"));
    }

    @Test
    void checkPwFindUser() {
        int checkPw = userInfoDAO.checkPwFindUser("0000000000", "김사장",
            "01000000000");
        assertEquals(1, checkPw);

        //log.info("checkPwFindUser result={}", userInfoDAO.checkPwFindUser());
    }
}