package com.oopsw.kostaerpserver.service;

import com.oopsw.kostaerpserver.vo.FoodMaterial;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class FoodMaterialServiceTest {

    @Autowired
    FoodMaterialService foodMaterialService;

    String bId = "0000000000";

    @Test
    void getFoodMaterialCountTest() {
        int count = foodMaterialService.getFoodMaterialCount(bId);

        System.out.println("count = " + count);

        assertTrue(count > 0);
    }

    @Test
    void getFoodMaterialListTest() {
        List<FoodMaterial> list =
                foodMaterialService.getFoodMaterialList(bId, "idDesc", 1, 5);

        list.forEach(System.out::println);

        assertTrue(list.size() > 0);
    }

    @Test
    void getFoodMaterialListSortTest() {
        List<FoodMaterial> list =
                foodMaterialService.getFoodMaterialList(bId, "expAsc", 1, 5);

        list.forEach(System.out::println);

        assertTrue(list.size() > 0);
    }

    @Test
    void searchFoodMaterialTest() {
        List<FoodMaterial> list =
                foodMaterialService.searchFoodMaterial(bId, "햄");

        list.forEach(System.out::println);

        assertTrue(list.size() > 0);
    }

    @Test
    @Transactional
    void deleteFoodMaterialTest() {
        List<FoodMaterial> list =
                foodMaterialService.getFoodMaterialList(bId, "idDesc", 1, 5);

        assertTrue(list.size() > 0);

        String foodMaterialId = list.get(0).getFoodMaterialId();

        foodMaterialService.deleteFoodMaterial(foodMaterialId, bId);

        System.out.println("삭제 테스트 대상 = " + foodMaterialId);
    }
}