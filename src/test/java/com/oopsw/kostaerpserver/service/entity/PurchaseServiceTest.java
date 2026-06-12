package com.oopsw.kostaerpserver.service.entity;

import com.oopsw.kostaerpserver.repository.entity.purchase.Purchase;
import com.oopsw.kostaerpserver.service.entity.purchase.PurchaseService;
import com.oopsw.kostaerpserver.vo.entity.PurchaseVO;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Slf4j
@SpringBootTest
@ActiveProfiles("test")
public class PurchaseServiceTest {
    @Autowired
    private PurchaseService purchaseService;

    @Test
    public void addPurchaseTest() {
        Assertions.assertTrue(purchaseService.addPurchase(PurchaseVO.builder().
                foodMaterialName("참치").
                foodMaterialCount(5).
                foodMaterialWeight(1000).
                totalWeight(5000).
                foodMaterialPrice(20000).
                totalPrice(100000).
                vender("김치집").
                incomeDate(LocalDateTime.parse(String.valueOf(LocalDateTime.now()))).
                expirationDate(LocalDateTime.parse(String.valueOf(LocalDateTime.now().plusDays(100)))).
                bId("1234567890").
                build()));

        log.info("insert = {}", purchaseService);
    }

    @Test
    public void getPurchaseListTest() {
        Assertions.assertNotNull(purchaseService.getPurchaseList("1234567890"));

        log.info("list = {}", purchaseService);
    }
}
