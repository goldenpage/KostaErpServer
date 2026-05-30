package com.oopsw.kostaerpserver.repository;

import com.oopsw.kostaerpserver.vo.Menu;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;
@Slf4j
@ActiveProfiles("test")
@SpringBootTest
public class MenuDAOTest {

    @Autowired
    MenuDAO menuDAO;

    @Test
    void getMenuListTest() {
        List<Menu> list = menuDAO.getMenuList("0000000000");

        list.forEach(menu -> log.info("menu = {}", menu));

        assertTrue(list.size() > 0);
    }

    @Test
    void getMenuDetailTest() {
        List<Menu> list = menuDAO.getMenuDetail("MI001");

        list.forEach(menu -> log.info("menu detail = {}", menu));

        assertTrue(list.size() > 0);
    }

    @Test
    void getLackMaterialCountTest() {
        int count = menuDAO.getLackMaterialCount("MI001", 1);
        log.info("lack count = {}", count);

        assertTrue(count >= 0);
    }

    @Test
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
}