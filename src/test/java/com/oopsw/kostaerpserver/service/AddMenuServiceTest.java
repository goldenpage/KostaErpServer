package com.oopsw.kostaerpserver.service;

import com.oopsw.kostaerpserver.repository.AddFoodMaterialDAO;
import com.oopsw.kostaerpserver.repository.AddMenuDAO;
import com.oopsw.kostaerpserver.vo.AddMenu;
import com.oopsw.kostaerpserver.vo.MenuCategory;
import com.oopsw.kostaerpserver.vo.Used;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SpringBootTest
@Transactional
@Slf4j
@ActiveProfiles("test")
public class AddMenuServiceTest {
    @Autowired
    AddMenuService addMenuService;

    @Autowired
    AddMenuDAO addMenuDAO;

    @Autowired
    AddFoodMaterialDAO addFoodMaterialDAO;

    // 1. 메뉴 입력
    @Test
    void addMenu() {
        String categoryId = addMenuDAO.getCategoryId("김밥류");

        AddMenu vo = new AddMenu();
        vo.setMenuId("M099");
        vo.setMenuName("테스트메뉴");
        vo.setMenuPrice(1000);
        vo.setMenuCategoryId(categoryId);

        int result = addMenuService.addMenu(vo);
        System.out.println(result);
        Assertions.assertTrue(result > 0);
    }

    // 2. 메뉴 ID 조회
    @Test
    void getNewMenuId_exists() {
        String categoryId = addMenuDAO.getCategoryId("김밥류");
        AddMenu vo = new AddMenu("MI001", "치즈김밥", 4000, categoryId);
        String menuId = addMenuService.getNewMenuId(vo);
        System.out.println(menuId);
        Assertions.assertNotNull(menuId);
    }

    // 3. 메뉴 카테고리 존재 여부 확인
    @Test
    void checkMenuCategoryExists_exists() {
        MenuCategory vo = new MenuCategory("MC006", "김밥류", "0000000000");
        int count = addMenuService.checkMenuCategoryExists(vo);
        System.out.println(count);
        Assertions.assertTrue(count > 0);
    }

    // 4. 메뉴 카테고리 추가
    @Test
    void addMenuCategory() {
        MenuCategory vo = new MenuCategory("MC099", "테스트메뉴카테고리", "0000000000");
        int result = addMenuService.addMenuCategory(vo);
        System.out.println(result);
        Assertions.assertTrue(result == 1);
    }

    // 5. 메뉴 카테고리 삭제
    @Test
    void deleteMenuCategory() {
        int result = addMenuService.deleteMenuCategory("떡볶이류");
        System.out.println(result);
        Assertions.assertTrue(result == 1);
    }

    // 6. 메뉴-식자재 연결 INSERT (USED 테이블)
    @Test
    void addUsedMaterial() {
        String foodMaterialId = addFoodMaterialDAO.getFoodMaterialListAll("0000000000").get(0).getFoodMaterialId();
        String categoryId = addMenuDAO.getCategoryId("김밥류");
        String menuId = addMenuDAO.getNewMenuId(new AddMenu("MI001", "치즈김밥", 4000, categoryId));

        Used used = new Used("U020", 5, foodMaterialId, menuId);
        int result = addMenuService.addUsedMaterial(used);
        System.out.println(result);
        Assertions.assertTrue(result > 0);
    }

    // 7. 사용중인 식자재 삭제
    @Test
    void deleteUsedMaterial() {
        int result = addMenuService.deleteUsedMaterial("99999");
        System.out.println(result);
        Assertions.assertTrue(result >= 0);
    }

    // 8. 판매 후 식자재 재고 차감
    @Test
    void updateFoodMaterialAfterSale() {
        Map<String, Object> params = new HashMap<>();
        params.put("menuId", "1");
        params.put("saleCount", 1);
        params.put("bId", "0000000000");

        int result = addMenuService.updateFoodMaterialAfterSale(params);
        System.out.println(result);
        Assertions.assertTrue(result >= 0);
    }

    // 9. 메뉴 카테고리 목록 조회
    @Test
    void getMenuCategoryList() {
        List<MenuCategory> list = addMenuService.getMenuCategoryList("0000000000");
        System.out.println(list);
        Assertions.assertNotNull(list);
    }

    // 10. 해당 카테고리에 메뉴 존재 여부
    @Test
    void hasMenuByCategory() {
        int count = addMenuService.hasMenuByCategory("한식");
        System.out.println(count);
        Assertions.assertTrue(count >= 0);
    }

    // 11. 메뉴명 중복 확인
    @Test
    void hasMenuCheck_exists() {
        String result = addMenuService.hasMenuCheck("치즈김밥");
        System.out.println(result);
        Assertions.assertNotNull(result);
    }

    // 12. 카테고리명으로 카테고리 ID 조회
    @Test
    void getCategoryId() {
        String categoryId = addMenuService.getCategoryId("김밥류");
        System.out.println(categoryId);
        Assertions.assertNotNull(categoryId);
    }
}
