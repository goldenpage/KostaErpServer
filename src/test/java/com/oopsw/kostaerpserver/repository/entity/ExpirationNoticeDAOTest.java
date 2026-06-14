package com.oopsw.kostaerpserver.repository.entity;


import com.oopsw.kostaerpserver.repository.dao.FoodMaterialDAO;
import com.oopsw.kostaerpserver.vo.FoodMaterial;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

@Slf4j
@ActiveProfiles("test")
@SpringBootTest
public class ExpirationNoticeDAOTest {
    @Autowired
    FoodMaterialDAO foodMaterialDAO;

    String bId = "0000000000";

    @Test
    void getExpirationNoticeListTest() {
        List<FoodMaterial> list =
                foodMaterialDAO.getExpirationNoticeList(bId, 3);

        list.forEach(food -> log.info("expiration food = {}", food));

        assertTrue(list.size() >= 0);
    }

    @Test
    void getExpirationNoticeListLargeDaysTest() {
        List<FoodMaterial> list =
                foodMaterialDAO.getExpirationNoticeList(bId, 9999);

        list.forEach(food -> log.info("expiration food = {}", food));

        assertTrue(list.size() >= 0);
    }
}