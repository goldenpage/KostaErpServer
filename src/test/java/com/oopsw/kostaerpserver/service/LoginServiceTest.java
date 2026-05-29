package com.oopsw.kostaerpserver.service;

import com.oopsw.kostaerpserver.vo.User;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@ActiveProfiles("test")
@SpringBootTest
public class LoginServiceTest {
    @Autowired
    LoginServiceImpl loginService;

    String bId = "0000000000";

    @Test
    void loginTest() {
        User user = loginService.login(bId, "test123");
        assertNotNull(user);
        assertEquals(bId,user.getBId());
    }

    @Test
    @Transactional
    void resisterTest() {
        int result =
            loginService.register(
                User.builder().bId("0000000004").name("김수장").pw("test123").phone(
                    "010"
                        + "-2222"
                    + "-3333").email("test@test.com").storeCategory("한식")
                    .storeName("테스트가게").storeType("음식점").build(), true);
        assertNotNull(result);
        assertTrue(result ==1);
    }

    @Test
    void checkMemberByVOTest() {
        User user = loginService.checkMemberByVO(bId, "김사장",
            "test123");

        assertNotNull(user);
        assertEquals(bId, user.getBId());
    }

    @Test
    @Transactional
    void setPwTest() {
        int result = loginService.setPw("test123", bId, "김사장",
            "01000000000");
        assertNotNull(result);
        assertTrue(result==1);
    }


    @Test
    void getPhoneCheckTest() {
        int result = loginService.getPhoneCheck("01000000000");
        assertNotNull(result);
        assertTrue(result==1);
    }

    @Test
    void getBidCheckTest() {
        int result = loginService.getBidCheck(bId);
        assertNotNull(result);
        assertTrue(result==1);
    }

    @Test
    void checkPwFindUserTest() {
        int result = loginService.checkPwFindUser(bId, "김사장",
            "01000000000");
        assertNotNull(result);
        assertTrue(result==1);
    }

}
