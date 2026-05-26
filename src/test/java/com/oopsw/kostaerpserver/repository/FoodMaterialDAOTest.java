package com.oopsw.kostaerpserver.repository;

import com.oopsw.kostaerpserver.vo.FoodMaterial;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class FoodMaterialDAOTest {

    @Autowired
    FoodMaterialDAO foodMaterialDAO;

    @Test
    void getFoodMaterialCountTest() {
        int count = foodMaterialDAO.getFoodMaterialCount("0000000000");
        System.out.println("count = " + count);

        assertTrue(count > 0);
    }

    @Test
    void getFoodMaterialListTest() {
        List<FoodMaterial> list = foodMaterialDAO.getFoodMaterialList(
                "0000000000",
                "idDesc",
                5,
                0
        );

        list.forEach(System.out::println);

        assertTrue(list.size() > 0);
    }

    @Test
    void getFoodMaterialByNameTest() {
        List<FoodMaterial> list = foodMaterialDAO.getFoodMaterialByName(
                "0000000000",
                "햄"
        );

        list.forEach(System.out::println);

        assertTrue(list.size() > 0);
    }

    @Test
    @Transactional
    void deleteFoodMaterialTest() {
        String foodMaterialId = "FM019";
        String bId = "0000000000";

        foodMaterialDAO.deleteUsedByFoodMaterial(foodMaterialId);
        foodMaterialDAO.deleteDisposalsByFoodMaterial(foodMaterialId);
        int result = foodMaterialDAO.deleteFoodMaterial(foodMaterialId, bId);

        System.out.println("delete result = " + result);

        assertTrue(result >= 0);
    }
}