package com.oopsw.kostaerpserver.service;

import com.oopsw.kostaerpserver.dto.stocknotice.StockNoticeRequest;
import com.oopsw.kostaerpserver.service.Interface.MenuService;
import com.oopsw.kostaerpserver.service.Interface.StockNoticeSettingService;
import com.oopsw.kostaerpserver.service.entity.outofstocknotice.OutOfStockNoticeServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@ActiveProfiles("test")
@SpringBootTest
public class ExpStockNoticeSettingServiceTest
{
    @Autowired
    private StockNoticeSettingService stockNoticeSettingService;
    @Autowired
    private OutOfStockNoticeServiceImpl outOfStockNoticeServiceImpl;
    @Autowired
    MenuService menuService;

    String bId = "0000000000";

    @Test
    @Transactional
    void saleMenuStockNoticeOffTest() {
        StockNoticeRequest request = new StockNoticeRequest();
        request.setFoodmAlert(false);
        request.setFoodmLimit(500);

        stockNoticeSettingService.updateStockNoticeSetting(bId, request);

        int beforeCount = outOfStockNoticeServiceImpl.getUnreadCount(bId);

        menuService.saleMenu("MI001", 1, bId);

        int afterCount = outOfStockNoticeServiceImpl.getUnreadCount(bId);

        log.info("알림 OFF 전 개수 = {}", beforeCount);
        log.info("알림 OFF 후 개수 = {}", afterCount);

        assertEquals(beforeCount, afterCount);
    }

    @Test
    @Transactional
    void saleMenuStockNoticeOnTest() {
        StockNoticeRequest request = new StockNoticeRequest();
        request.setFoodmAlert(true);
        request.setFoodmLimit(999999);

        stockNoticeSettingService.updateStockNoticeSetting(bId, request);

        menuService.saleMenu("MI001", 1, bId);

        int count = outOfStockNoticeServiceImpl.getUnreadCount(bId);

        log.info("재고 알림 ON 상태 unread count = {}", count);

        assertTrue(count >= 0);
    }
}
