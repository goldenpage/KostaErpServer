package com.oopsw.kostaerpserver.service;

import com.oopsw.kostaerpserver.dto.stocknotice.StockNoticeRequest;
import com.oopsw.kostaerpserver.dto.stocknotice.StockNoticeResponse;
import com.oopsw.kostaerpserver.service.entity.stocknotice.StockNoticeSettingService;
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
public class StockNoticeSettingServiceTest {
    @Autowired
    private StockNoticeSettingService stockNoticeSettingService;

    private final String testBId = "9999999999";

    @Test
    void stockNoticeSettingTest() {
        StockNoticeResponse response =
                stockNoticeSettingService.getStockNoticeSetting(testBId);

        log.info("response = {}", response);

        Assertions.assertEquals(testBId, response.getBId());
        Assertions.assertTrue(response.isFoodmAlert());
        Assertions.assertEquals(5, response.getFoodmLimit());
    }

    @Test
    void stockNoticeSettingUpdateTest() {
        StockNoticeRequest request = new StockNoticeRequest();
        request.setFoodmAlert(false);
        request.setFoodmLimit(10);

        StockNoticeResponse response =
                stockNoticeSettingService.updateStockNoticeSetting(testBId, request);

        log.info("updated response = {}", response);

        Assertions.assertEquals(testBId, response.getBId());
        Assertions.assertFalse(response.isFoodmAlert());
        Assertions.assertEquals(10, response.getFoodmLimit());
    }

    @Test
    void foodmLimitCorrectionTest() {
        StockNoticeRequest request = new StockNoticeRequest();
        request.setFoodmAlert(true);
        request.setFoodmLimit(0);

        StockNoticeResponse response =
                stockNoticeSettingService.updateStockNoticeSetting(testBId, request);

        log.info("corrected response = {}", response);

        Assertions.assertEquals(testBId, response.getBId());
        Assertions.assertTrue(response.isFoodmAlert());
        Assertions.assertEquals(1, response.getFoodmLimit());
    }
}