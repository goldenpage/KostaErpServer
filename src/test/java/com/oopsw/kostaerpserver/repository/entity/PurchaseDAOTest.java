package com.oopsw.kostaerpserver.repository.entity;

import com.oopsw.kostaerpserver.repository.entity.purchase.Purchase;
import com.oopsw.kostaerpserver.repository.entity.purchase.PurchaseRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Slf4j
@SpringBootTest
@ActiveProfiles("test")
public class PurchaseDAOTest {
    @Autowired
    private PurchaseRepository purchaseRepository;

    @Test
    public void addPurchaseTest() {
        purchaseRepository.save(Purchase.builder().
                foodMaterialName("김치").
                foodMaterialCount(5).
                foodMaterialWeight(5000).
                totalWeight(25000).
                foodMaterialPrice(20000).
                totalPrice(100000).
                vender("김치집").
                incomeDate(LocalDateTime.now()).
                expirationDate(LocalDate.parse("2030-06-09")).
                build());

        log.info("saved notice = {}", purchaseRepository);
    }
}
