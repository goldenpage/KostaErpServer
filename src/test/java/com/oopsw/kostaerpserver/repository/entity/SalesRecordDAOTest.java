package com.oopsw.kostaerpserver.repository.entity;

import com.oopsw.kostaerpserver.repository.entity.salesrecord.*;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;

@ActiveProfiles("test")
@SpringBootTest
@Transactional
class SalesRepositoryTest {

    @Autowired
    private SalesRecordRepository salesRecordRepository;

    @Autowired
    private MenuRepository menuRepository;

    @Autowired
    private RevenueRepository revenueRepository;

    @Autowired
    private EntityManager em;

    @Test
    void saveTest() {

        Menu menu = new Menu();
        menu.setMenuId("MI001");
        menu.setMenuName("아메리카노");
        menu.setMenuPrice(3000);

        menuRepository.save(menu);

        Revenue revenue = new Revenue();
        revenue.setRevenueId("RV001");
        revenue.setRevenueDate(LocalDate.now());

        revenueRepository.save(revenue);

        SalesRecord sales = new SalesRecord();
        sales.setSaleId("S001");
        sales.setSaleMenuCount(3);
        sales.setMenu(menu);
        sales.setRevenue(revenue);

        salesRecordRepository.save(sales);

        em.flush();
        em.clear();

        SalesRecord result = salesRecordRepository.findById("S001").orElseThrow();

        assertEquals(3, result.getSaleMenuCount());
        assertEquals("MI001", result.getMenu().getMenuId());
    }
}