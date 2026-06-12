package com.oopsw.kostaerpserver.service.entity;

import com.oopsw.kostaerpserver.dto.salesrecord.SalesRecordResponse;
import com.oopsw.kostaerpserver.repository.entity.salesrecord.*;
import com.oopsw.kostaerpserver.service.Interface.MenuService;
import jakarta.persistence.EntityManager;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;

@Slf4j
@SpringBootTest
@Transactional
@ActiveProfiles("test")
public class SalesRecordServiceImplTest {
    @Autowired
    private SalesRecordService salesRecordService;

    @Autowired
    private MenuRepository menuRepository;

    @Autowired
    private RevenueRepository revenueRepository;

    @Autowired
    private MenuCategoryRepository menuCategoryRepository;

    @Autowired
    private SalesRecordRepository salesRecordRepository;

    @Autowired
    EntityManager em;

    @MockitoBean
    private MenuService menuService;


    //테스트용 데이터 생성
    private Menu createMenu() {

        MenuCategory category = new MenuCategory();
        category.setMenuCategoryId("C001");
        category.setMenuCategory("COFFEE");
        category.setBId("B001");
        menuCategoryRepository.save(category);

        Menu menu = new Menu();
        menu.setMenuId("M001");
        menu.setMenuName("아메리카노");
        menu.setMenuPrice(3000);
        menu.setMenuCategory(category);

        return menuRepository.save(menu);
    }

    //판매 등록 테스트
    @Test
    void addSale_test() {

        doNothing().when(menuService)
                .saleMenu(anyString(), anyInt(), anyString(), anyString());

        createMenu();

        salesRecordService.addSale("M001", 2, "B001", "card");

        em.flush();
        em.clear();

        List<SalesRecord> result = salesRecordRepository.findAll();

        assertThat(result)
                .extracting(SalesRecord::getSaleMenuCount)
                .contains(2);
    }

    //판매 목록 조회 테스트
    @Test
    void getSalesList_test() {
        Menu menu = createMenu();

        Revenue revenue = new Revenue();
        revenue.setRevenueId("RV001");
        revenue.setRevenueDate(LocalDate.now());
        revenue.setPayment("CARD");

        revenueRepository.save(revenue);

        SalesRecord record = new SalesRecord();
        record.setSaleId("S001");
        record.setMenu(menu);
        record.setRevenue(revenue);
        record.setSaleMenuCount(3);

        salesRecordRepository.save(record);

        var result =salesRecordService.getSalesList(1, 10);

        log.info("===== 판매 목록 조회 =====");

        result.getContent().forEach(dto ->
                log.info(
                        "saleId={}, menuName={}, qty={}, totalPrice={}, payment={}",
                        dto.getSaleId(),
                        dto.getMenuName(),
                        dto.getQty(),
                        dto.getTotalPrice(),
                        dto.getPaymentMethod()
                )
        );

        assertThat(result.getContent()).isNotEmpty();
        assertThat(result.getContent().get(0).getMenuName()).isEqualTo("아메리카노");
    }


    //날짜 검색 테스트
    @Test
    void getSalesByDate_test() {
        Menu menu = createMenu();

        Revenue revenue = new Revenue();
        revenue.setRevenueId("RV002");
        revenue.setRevenueDate(LocalDate.of(2026, 4, 1));
        revenue.setPayment("CARD");

        revenueRepository.save(revenue);

        SalesRecord record = new SalesRecord();
        record.setSaleId("S002");
        record.setMenu(menu);
        record.setRevenue(revenue);
        record.setSaleMenuCount(1);

        salesRecordRepository.save(record);

        List<SalesRecordResponse> result = salesRecordService.getSalesByDate("2026-04-01", "2026-04-02");

        log.info("===== 날짜 검색 결과 =====");

        result.forEach(dto ->
                log.info(
                        "saleId={}, revenueId={}, menuName={}, qty={}, totalPrice={}",
                        dto.getSaleId(),
                        dto.getRevenueId(),
                        dto.getMenuName(),
                        dto.getQty(),
                        dto.getTotalPrice()
                )
        );

        assertThat(result).hasSize(1);

        assertThat(result.get(0).getRevenueId()).isEqualTo("RV002");
    }
}
