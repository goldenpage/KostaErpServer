package com.oopsw.kostaerpserver.repository.entity;

import com.oopsw.kostaerpserver.repository.entity.stocknotice.StockNoticeSetting;
import com.oopsw.kostaerpserver.repository.entity.stocknotice.StockNoticeSettingRepository;
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
public class StockNoticeSettingDAOTest {
    @Autowired
    private StockNoticeSettingRepository stockNoticeSettingRepository;

    private final String testBId = "9999999999";

    @Test
    void saveStockNoticeSettingTest() {
        StockNoticeSetting setting = StockNoticeSetting.builder()
                .bId(testBId)
                .foodmAlert(true)
                .foodmLimit(5)
                .build();

        StockNoticeSetting saved = stockNoticeSettingRepository.save(setting);

        log.info("saved setting = {}", saved);

        Assertions.assertTrue(saved.getStockNoticeSettingId() > 0);
        Assertions.assertEquals(testBId, saved.getBId());
        Assertions.assertTrue(saved.isFoodmAlert());
        Assertions.assertEquals(5, saved.getFoodmLimit());
    }

    @Test
    void findByBIdTest() {
        StockNoticeSetting setting = StockNoticeSetting.builder()
                .bId(testBId)
                .foodmAlert(true)
                .foodmLimit(5)
                .build();

        stockNoticeSettingRepository.save(setting);

        StockNoticeSetting found = stockNoticeSettingRepository.findByBId(testBId)
                .orElseThrow();

        log.info("found setting = {}", found);

        Assertions.assertEquals(testBId, found.getBId());
        Assertions.assertTrue(found.isFoodmAlert());
        Assertions.assertEquals(5, found.getFoodmLimit());
    }

    @Test
    void updateStockNoticeSettingTest() {
        StockNoticeSetting setting = StockNoticeSetting.builder()
                .bId(testBId)
                .foodmAlert(true)
                .foodmLimit(5)
                .build();

        StockNoticeSetting saved = stockNoticeSettingRepository.save(setting);

        saved.update(false, 10);

        log.info("updated setting = {}", saved);

        Assertions.assertFalse(saved.isFoodmAlert());
        Assertions.assertEquals(10, saved.getFoodmLimit());
    }

    @Test
    void foodmLimitTest() {
        StockNoticeSetting setting = StockNoticeSetting.builder()
                .bId(testBId)
                .foodmAlert(true)
                .foodmLimit(5)
                .build();

        StockNoticeSetting saved = stockNoticeSettingRepository.save(setting);

        saved.update(true, 0);

        log.info("limit corrected setting = {}", saved);

        Assertions.assertTrue(saved.isFoodmAlert());
        Assertions.assertEquals(1, saved.getFoodmLimit());
    }
}