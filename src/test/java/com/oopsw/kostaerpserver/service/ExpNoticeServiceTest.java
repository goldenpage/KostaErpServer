package com.oopsw.kostaerpserver.service;

import com.oopsw.kostaerpserver.dto.expnotice.ExpNoticeRequest;
import com.oopsw.kostaerpserver.dto.expnotice.ExpNoticeResponse;
import com.oopsw.kostaerpserver.service.entity.expdate.ExpNoticeService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class ExpNoticeServiceTest {
    @Autowired
    private ExpNoticeService expNoticeService;

    private final String testBId = "9999999999";

    @Test
    void expNoticeTest() {
        ExpNoticeResponse response = expNoticeService.getExpNotice(testBId);

        log.info("response bId = {}", response.getBId());
        log.info("response expAlert = {}", response.isExpAlert());
        log.info("response expDays = {}", response.getExpDays());

        Assertions.assertEquals(testBId, response.getBId());
        Assertions.assertTrue(response.isExpAlert());
        Assertions.assertEquals(3, response.getExpDays());
    }

    @Test
    void expDayUpdateTest() {
        ExpNoticeRequest request = new ExpNoticeRequest();
        request.setExpAlert(false);
        request.setExpDays(5);

        ExpNoticeResponse response =
                expNoticeService.updateExpNotice(testBId, request);

        log.info("updated bId = {}", response.getBId());
        log.info("updated expAlert = {}", response.isExpAlert());
        log.info("updated expDays = {}", response.getExpDays());

        Assertions.assertEquals(testBId, response.getBId());
        Assertions.assertFalse(response.isExpAlert());
        Assertions.assertEquals(5, response.getExpDays());
    }

    @Test
    void expDaysTest() {
        ExpNoticeRequest request = new ExpNoticeRequest();
        request.setExpAlert(true);
        request.setExpDays(0);

        ExpNoticeResponse response =
                expNoticeService.updateExpNotice(testBId, request);

        log.info("보정된 expDays = {}", response.getExpDays());

        Assertions.assertTrue(response.isExpAlert());
        Assertions.assertEquals(1, response.getExpDays());
    }
}