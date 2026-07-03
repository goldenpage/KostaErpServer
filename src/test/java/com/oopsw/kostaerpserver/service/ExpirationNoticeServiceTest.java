package com.oopsw.kostaerpserver.service;

import com.oopsw.kostaerpserver.dto.expnotice.ExpNoticeRequest;
import com.oopsw.kostaerpserver.dto.expnotice.ExpirationNoticeResponse;
import com.oopsw.kostaerpserver.service.entity.expdate.ExpNoticeService;
import com.oopsw.kostaerpserver.service.entity.expdate.ExpirationNoticeService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Slf4j
@ActiveProfiles("test")
@SpringBootTest
public class ExpirationNoticeServiceTest {
    @Autowired
    ExpirationNoticeService expirationNoticeService;

    @Autowired
    ExpNoticeService expNoticeService;

    String bId = "0000000000";

    @Test
    @Transactional
    void getExpirationNoticeListTest() {
        ExpNoticeRequest request = new ExpNoticeRequest();
        request.setExpAlert(true);
        request.setExpDays(3);

        expNoticeService.updateExpNotice(bId, request);

        List<ExpirationNoticeResponse> list =
                expirationNoticeService.getExpirationNoticeList(bId);

        list.forEach(notice -> log.info("expiration notice = {}", notice));

        assertTrue(list.size() >= 0);
    }

    @Test
    @Transactional
    void getExpirationNoticeCountTest() {
        ExpNoticeRequest request = new ExpNoticeRequest();
        request.setExpAlert(true);
        request.setExpDays(3);

        expNoticeService.updateExpNotice(bId, request);

        List<ExpirationNoticeResponse> list =
                expirationNoticeService.getExpirationNoticeList(bId);

        int count =
                expirationNoticeService.getExpirationNoticeCount(bId);

        log.info("list size = {}", list.size());
        log.info("count = {}", count);

        assertEquals(list.size(), count);
    }

    @Test
    @Transactional
    void expAlertOffTest() {
        ExpNoticeRequest request = new ExpNoticeRequest();
        request.setExpAlert(false);
        request.setExpDays(3);

        expNoticeService.updateExpNotice(bId, request);

        List<ExpirationNoticeResponse> list =
                expirationNoticeService.getExpirationNoticeList(bId);

        int count =
                expirationNoticeService.getExpirationNoticeCount(bId);

        log.info("expAlert off list = {}", list);
        log.info("expAlert off count = {}", count);

        assertEquals(0, list.size());
        assertEquals(0, count);
    }
}