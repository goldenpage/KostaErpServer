package com.oopsw.kostaerpserver.service;

import com.oopsw.kostaerpserver.vo.Menu;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@Slf4j
@ActiveProfiles("test")
@SpringBootTest
public class MenuServiceTest {

    @Autowired
    MenuService menuService;

    String bId = "0000000000";

    @Test
    void getMenuListTest() {
        List<Menu> list = menuService.getMenuList(bId);

        list.forEach(menu -> log.info("menu = {}", menu));

        assertTrue(list.size() > 0);
    }

    @Test
    void getMenuDetailTest() {
        List<Menu> list = menuService.getMenuDetail("MI001");

        list.forEach(menu -> log.info("menu detail = {}", menu));

        assertTrue(list.size() > 0);
    }

    @Test
    @Transactional
    void saleMenuTest() {
        menuService.saleMenu("MI001", 1, bId);

        log.info("판매 차감 테스트 완료");
    }

    @Test
    void saleMenuInvalidCountTest() {
        assertThrows(RuntimeException.class, () -> {
            menuService.saleMenu("MI001", 0, bId);
        });
    }
}