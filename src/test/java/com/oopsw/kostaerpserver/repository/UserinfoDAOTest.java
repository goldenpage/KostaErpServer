package com.oopsw.kostaerpserver.repository;

import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Mapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@Slf4j
@SpringBootTest
public class UserinfoDAOTest {
    @Autowired
    UserInfoDAO userInfoDAO;

    @Test
    void loginTest() {
        log.info(userInfoDAO.login());
    }

    @Test
    void register() {
        log.info(userInfoDAO.register());
    }

    @Test
    void checkMemberByVO() {
        log.info(userInfoDAO.checkMemberByVO());
    }

    @Test
    void setPw() {
        log.info(userInfoDAO.setPw());
    }

    @Test
    void getPhoneCheck() {
        log.info(userInfoDAO.getPhoneCheck());
    }

    @Test
    void getBidCheck() {
        log.info(userInfoDAO.getBidCheck());
    }

    @Test
    void checkPwFindUser() {
        log.info(userInfoDAO.checkPwFindUser());
    }
}
