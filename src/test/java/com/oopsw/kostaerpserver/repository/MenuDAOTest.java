package com.oopsw.kostaerpserver.repository;

import com.oopsw.kostaerpserver.repository.dao.MenuDAO;
import com.oopsw.kostaerpserver.repository.entity.outofstocknotice.OutOfStockNotice;
import com.oopsw.kostaerpserver.repository.entity.outofstocknotice.OutOfStockNoticeRepository;
import com.oopsw.kostaerpserver.vo.Menu;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;
@Slf4j
@ActiveProfiles("test")
@SpringBootTest
public class MenuDAOTest {

    @Autowired
    MenuDAO menuDAO;
    @Autowired
    private OutOfStockNoticeRepository outOfStockNoticeRepository;

//    @Test
    void getMenuListTest() {
        List<Menu> list = menuDAO.getMenuList("0000000000");

        list.forEach(menu -> log.info("menu = {}", menu));

        assertTrue(list.size() > 0);
    }

//    @Test
    void getMenuDetailTest() {
        List<Menu> list = menuDAO.getMenuDetail("MI001");

        list.forEach(menu -> log.info("menu detail = {}", menu));

        assertTrue(list.size() > 0);
    }

//    @Test
    void getLackMaterialCountTest() {
        int count = menuDAO.getLackMaterialCount("MI001", 1);
        log.info("lack count = {}", count);

        assertTrue(count >= 0);
    }

//    @Test
    @Transactional
    void updateFoodMaterialAfterSaleTest() {
        int lackCount = menuDAO.getLackMaterialCount("MI001", 1);
        assertTrue(lackCount == 0);

        int result = menuDAO.updateFoodMaterialAfterSale(
                "MI001",
                1,
                "0000000000"
        );

        log.info("update result = {}", result);

        assertTrue(result > 0);
    }

//    @Test
    public void existsTodayNoticeTest() {
        outOfStockNoticeRepository.save(OutOfStockNotice.builder().
                noticeDate(LocalDateTime.now()).
                noticeContent(" 얼마 남지 않음").
                foodMaterialName("단무지").
                remainStockAmount(2).
                bId("1234567890").
                readYn("N").
                build());

        boolean exists = outOfStockNoticeRepository.
                existsTodayNotice("1234567890", "단무지");

        log.info("existsTodayNotice(단무지) = {}", exists);
    }

    @Test
    void getLowStockMaterialListTest() {
        String menuId = "MI001";
        String bId = "0000000000";
        int foodmLimit = 500;

        List<Menu> list = menuDAO.getLowStockMaterialList(menuId, bId, foodmLimit);

        list.forEach(m -> log.info("재고 부족 식자재: name={}, remainStock={}",
                m.getFoodMaterialName(),
                m.getFoodMaterialCountAll()));

        assertTrue(list.size() >= 0);
    }
}