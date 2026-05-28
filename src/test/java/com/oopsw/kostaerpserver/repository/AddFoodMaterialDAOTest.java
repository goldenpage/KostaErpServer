package com.oopsw.kostaerpserver.repository;

import com.oopsw.kostaerpserver.vo.FoodCategory;
import com.oopsw.kostaerpserver.vo.FoodMaterial;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

//import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.sql.Date;

@SpringBootTest
@Transactional
public class AddFoodMaterialDAOTest {
    @Autowired
    AddFoodMaterialDAO addFoodMaterialDAO;

    @Test
    void getFoodCategoryList() {
        List<FoodCategory> list = addFoodMaterialDAO.getFoodCategoryList();
        System.out.println(list);
        Assertions.assertNotNull(list);
    }

    @Test
    void checkFoodCategoryExists() {
        int count = addFoodMaterialDAO.checkFoodCategoryExists("정육");
        System.out.println(count);
        Assertions.assertTrue(count > 0);
    }

    @Test
    void addFoodCategory() {
        int result = addFoodMaterialDAO.addFoodCategory("테스트카테고리");
        System.out.println(result);
        Assertions.assertTrue(result == 1);
    }

    @Test
    void deleteFoodCategory() {
        int result = addFoodMaterialDAO.deleteFoodCategory("반찬");
        System.out.println(result);
        Assertions.assertTrue(result == 1);
    }

    @Test
    void getCategoryId() {
        String categoryId = addFoodMaterialDAO.getCategoryId("정육");
        System.out.println(categoryId);
        Assertions.assertNotNull(categoryId);
    }

    @Test
    void hasFoodMaterialByCategory() {
        int count = addFoodMaterialDAO.hasFoodMaterialByCategory("육류");
        System.out.println(count);
        Assertions.assertTrue(count >= 0);
    }

    @Test
    void getFoodMaterialCount() {
        int count = addFoodMaterialDAO.getFoodMaterialCount("0000000000");
        System.out.println(count);
        Assertions.assertTrue(count >= 0);
    }

    @Test
    void addFoodMaterial() {
        String categoryId = addFoodMaterialDAO.getCategoryId("정육");

        FoodMaterial vo = new FoodMaterial();
        vo.setFoodMaterialName("테스트식자재");
        vo.setFoodCategory(categoryId);
        vo.setFoodMaterialCount(10);
        vo.setFoodMaterialCountAll(10000);
        vo.setFoodMaterialPrice(5000);
        vo.setFoodMaterialType("고체");
        vo.setVender("테스트업체");
        vo.setIncomeDate(Date.valueOf("2026-01-01"));
        vo.setExpirationDate(Date.valueOf("2026-12-31"));
        vo.setBId("0000000000");

        int result = addFoodMaterialDAO.addFoodMaterial(vo);
        System.out.println(result);
        Assertions.assertTrue(result == 1);
    }

    @Test
    void getFoodMaterialList() {
        Map<String, Object> params = new HashMap<>();
        params.put("bId", "0000000000");
        params.put("orderBy", "f.foodMaterial_Id DESC");
        params.put("offset", 0);
        params.put("pageSize", 10);

        List<FoodMaterial> list = addFoodMaterialDAO.getFoodMaterialList(params);
        System.out.println(list);
        Assertions.assertNotNull(list);
    }

    @Test
    void getFoodMaterialByName() {
        Map<String, Object> params = new HashMap<>();
        params.put("foodMaterialName", "양");
        params.put("bId", "0000000000");

        List<FoodMaterial> list = addFoodMaterialDAO.getFoodMaterialByName(params);
        System.out.println(list);
        Assertions.assertNotNull(list);
    }

    @Test
    void getFoodMaterialListAll() {
        List<FoodMaterial> list = addFoodMaterialDAO.getFoodMaterialListAll("0000000000");
        System.out.println(list);
        Assertions.assertNotNull(list);
    }

    @Test
    void getFoodMaterialDetail() {
        // DB에 실제 존재하는 foodMaterialId로 변경 필요
        FoodMaterial vo = addFoodMaterialDAO.getFoodMaterialDetail("FM001");
        System.out.println(vo);
        Assertions.assertNotNull(vo);
    }

    @Test
    void deleteFoodMaterial() {
        // deleteUsedByFoodMaterial → deleteDisposalsByFoodMaterial → deleteFoodMaterial 순서로 호출
        // DB에 실제 존재하는 foodMaterialId로 변경 필요
        String foodMaterialId = "99999";

        addFoodMaterialDAO.deleteUsedByFoodMaterial(foodMaterialId);
        addFoodMaterialDAO.deleteDisposalsByFoodMaterial(foodMaterialId);

        Map<String, Object> params = new HashMap<>();
        params.put("foodMaterialId", foodMaterialId);
        params.put("bId", "0000000000");
        int result = addFoodMaterialDAO.deleteFoodMaterial(params);

        System.out.println(result);
        Assertions.assertTrue(result >= 0);
    }

    @Test
    void getFoodMaterialTotalAmount() {
        Map<String, Object> params = new HashMap<>();
        params.put("bId", "0000000000");
        params.put("startDate", "2025-01-01");
        params.put("endDate", "2025-12-31");

        int total = addFoodMaterialDAO.getFoodMaterialTotalAmount(params);
        System.out.println(total);
        Assertions.assertTrue(total >= 0);
    }

    @Test
    void getFoodMaterialSpendingRank() {
        Map<String, Object> params = new HashMap<>();
        params.put("bId", "0000000000");
        params.put("startDate", "2025-01-01");
        params.put("endDate", "2025-12-31");

        List<FoodMaterial> list = addFoodMaterialDAO.getFoodMaterialSpendingRank(params);
        System.out.println(list);
        Assertions.assertNotNull(list);
    }
}
