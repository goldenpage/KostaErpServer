package com.oopsw.kostaerpserver.service;

import com.oopsw.kostaerpserver.repository.AddFoodMaterialDAO;
import com.oopsw.kostaerpserver.vo.FoodCategory;
import com.oopsw.kostaerpserver.vo.FoodMaterial;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SpringBootTest
@Transactional
@Slf4j
@ActiveProfiles("test")
public class AddFoodMaterialServiceTest {
    @Autowired
    AddFoodMaterialService addFoodMaterialService;
    @Autowired
    private AddFoodMaterialDAO addFoodMaterialDAO;

    // 1. 사용하고 있는 식자재 개수 조회
    @Test
    void getFoodMaterialCount() {
        int count = addFoodMaterialService.getFoodMaterialCount("0000000000");
        log.info("getFoodMaterialCount: {}", count);
        Assertions.assertTrue(count >= 0);
    }

    // 2. 식자재 입력
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

        int result = addFoodMaterialService.addFoodMaterial(vo);
        log.info("addFoodMaterial(): {}", vo.getFoodMaterialName(), result);
        Assertions.assertTrue(result == 1);
    }

    // 3. 카테고리 여부 체크 - 존재하는 카테고리
    @Test
    void checkFoodCategoryExists_exists() {
        int count = addFoodMaterialService.checkFoodCategoryExists("정육");
        log.info("checkFoodCategoryExists: {}", count);
        Assertions.assertTrue(count > 0);
    }

    // 3. 카테고리 여부 체크 - 존재하지 않는 카테고리
    @Test
    void checkFoodCategoryExists_notExists() {
        int count = addFoodMaterialService.checkFoodCategoryExists("없는카테고리");
        log.info("checkFoodCategoryExists() : {}", count);
        Assertions.assertTrue(count == 0);
    }

    // 4. 카테고리 추가
    @Test
    void addFoodCategory() {
        FoodCategory vo = new FoodCategory();
        vo.setFoodCategoryId("FC005");
        vo.setFoodCategory("테스트카테고리");

        int result = addFoodMaterialService.addFoodCategory(vo);
        log.info("addFoodCategory(): {}", vo.getFoodCategory(), result);
        Assertions.assertTrue(result == 1);
    }

    // 5. 카테고리 삭제
    @Test
    void deleteFoodCategory() {
        int result = addFoodMaterialService.deleteFoodCategory("반찬");
        log.info("deleteFoodCategory(): {}", result);
        Assertions.assertTrue(result == 1);
    }

    // 6. 식자재 이름 검색
    @Test
    void getFoodMaterialByName() {
        Map<String, Object> params = new HashMap<>();
        params.put("foodMaterialName", "양");
        params.put("bId", "0000000000");

        List<FoodMaterial> list = addFoodMaterialService.getFoodMaterialByName(params);
        log.info("getFoodMaterialByName: {}", list);
        Assertions.assertNotNull(list);
    }

    // 7. 식자재 목록 조회
    @Test
    void getFoodMaterialList() {
        Map<String, Object> params = new HashMap<>();
        params.put("bId", "0000000000");
        params.put("orderBy", "f.foodMaterial_Id DESC");
        params.put("offset", 0);
        params.put("pageSize", 10);

        List<FoodMaterial> list = addFoodMaterialService.getFoodMaterialList(params);
        log.info("getFoodMaterialList: {}", list);
        Assertions.assertNotNull(list);
    }

    // 8. 식자재 상세 조회
    @Test
    void getFoodMaterialDetail() {
        FoodMaterial vo = addFoodMaterialService.getFoodMaterialDetail("FM001");
        log.info("getFoodMaterialDetail(): {}", vo);
        Assertions.assertNotNull(vo);
    }

    // 9. 식자재 삭제 전처리 - USED 삭제
    @Test
    void deleteUsedByFoodMaterial() {
        int result = addFoodMaterialService.deleteUsedByFoodMaterial("99999");
        log.info("deleteUsedByFoodMaterial(): {}", result);
        Assertions.assertTrue(result >= 0);
    }

    // 10. 식자재 삭제 전처리 - DISPOSALS 삭제
    @Test
    void deleteDisposalsByFoodMaterial() {
        int result = addFoodMaterialService.deleteDisposalsByFoodMaterial("99999");
        log.info("deleteDisposalsByFoodMaterial(): {}", result);
        Assertions.assertTrue(result >= 0);
    }

    // 11. 식자재 삭제
    @Test
    void deleteFoodMaterial() {
        String foodMaterialId = "99999";

        addFoodMaterialService.deleteUsedByFoodMaterial(foodMaterialId);
        addFoodMaterialService.deleteDisposalsByFoodMaterial(foodMaterialId);

        Map<String, Object> params = new HashMap<>();
        params.put("foodMaterialId", foodMaterialId);
        params.put("bId", "0000000000");

        int result = addFoodMaterialService.deleteFoodMaterial(params);
        log.info("deleteFoodMaterial(): {}", foodMaterialId, result);
        Assertions.assertTrue(result >= 0);
    }

    // 12. 식자재 총 구매금액 조회
    @Test
    void getFoodMaterialTotalAmount() {
        Map<String, Object> params = new HashMap<>();
        params.put("bId", "0000000000");
        params.put("startDate", "2025-01-01");
        params.put("endDate", "2025-12-31");

        int total = addFoodMaterialService.getFoodMaterialTotalAmount(params);
        log.info("getFoodMaterialTotalAmount(): {}", total);
        Assertions.assertTrue(total >= 0);
    }

    // 13. 식자재 지출 랭킹 조회
    @Test
    void getFoodMaterialSpendingRank() {
        Map<String, Object> params = new HashMap<>();
        params.put("bId", "0000000000");
        params.put("startDate", "2025-01-01");
        params.put("endDate", "2025-12-31");

        List<FoodMaterial> list = addFoodMaterialService.getFoodMaterialSpendingRank(params);
        log.info("getFoodMaterialSpendingRank(): {}", list);
        Assertions.assertNotNull(list);
    }

    // 14. 카테고리 전체 목록 조회
    @Test
    void getFoodCategoryList() {
        List<FoodCategory> list = addFoodMaterialService.getFoodCategoryList();
        log.info("getFoodCategoryList()): {}", list);
        Assertions.assertNotNull(list);
    }

    // 15. 카테고리에 식자재 존재 여부
    @Test
    void hasFoodMaterialByCategory() {
        int count = addFoodMaterialService.hasFoodMaterialByCategory("육류");
        log.info("hasFoodMaterialByCategory(): {}", count);
        Assertions.assertTrue(count >= 0);
    }

    // 16. 식자재 전체 목록 조회
    @Test
    void getFoodMaterialListAll() {
        List<FoodMaterial> list = addFoodMaterialService.getFoodMaterialListAll("0000000000");
        log.info("getFoodMaterialListAll(): {}", list);
        Assertions.assertNotNull(list);
    }

    // 17. 카테고리 ID 조회
    @Test
    void getCategoryId() {
        String categoryId = addFoodMaterialService.getCategoryId("정육");
        log.info("getCategoryId(): {}", categoryId);
        Assertions.assertNotNull(categoryId);
    }
}
