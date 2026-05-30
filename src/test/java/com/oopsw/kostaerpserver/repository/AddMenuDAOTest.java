package com.oopsw.kostaerpserver.repository;

import com.oopsw.kostaerpserver.vo.AddMenu;
import com.oopsw.kostaerpserver.vo.Menu;
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
public class AddMenuDAOTest {
    @Autowired
    AddMenuDAO addMenuDAO;

    @Autowired
    AddFoodMaterialDAO addFoodMaterialDAO;

    @Test
    void getMenuCategoryList() {
        List<MenuCategory> list = addMenuDAO.getMenuCategoryList("0000000000");
        log.info("getMenuCategoryList: {}", list);
        Assertions.assertNotNull(list);
    }

    @Test
    void checkMenuCategoryExists() {
        MenuCategory vo = new MenuCategory("MC006","김밥류", "0000000000");
        int count = addMenuDAO.checkMenuCategoryExists(vo);
        log.info("checkMenuCategoryExists(): {}", count);
        Assertions.assertTrue(count > 0);
    }

    @Test
    void addMenuCategory() {
        MenuCategory vo = new MenuCategory("M007","테스트메뉴카테고리", "0000000000");
        int result = addMenuDAO.addMenuCategory(vo);
        log.info("addMenuCategory(): {}", vo.getMenuCategory(), result);
        Assertions.assertTrue(result == 1);
    }

    @Test
    void deleteMenuCategory() {
        int result = addMenuDAO.deleteMenuCategory("떡볶이류");
        log.info("deleteMenuCategory(): {}", result);
        Assertions.assertTrue(result == 1);
    }

    @Test
    void getCategoryId() {
        String categoryId = addMenuDAO.getCategoryId("김밥류");
        log.info("getCategoryId(): {}", categoryId);
        Assertions.assertNotNull(categoryId);
    }

    @Test
    void hasMenuByCategory() {
        int count = addMenuDAO.hasMenuByCategory("한식");
        log.info("hasMenuByCategory(): {}", count);
        Assertions.assertTrue(count >= 0);
    }

    @Test
    void addMenu() {
        AddMenu vo = new AddMenu();
        String categoryId = addMenuDAO.getCategoryId("김밥류");

        vo.setMenuId("M099");
        vo.setMenuName("테스트메뉴");
        vo.setMenuPrice(1000);
        vo.setMenuCategoryId(categoryId);

        int result = addMenuDAO.addMenu(vo);
        log.info("addMenu(): {}", vo.getMenuName(), result);
        Assertions.assertTrue(result > 0);
    }

    @Test
    void getNewMenuId() {
        AddMenu vo = new AddMenu("M009","테스트메뉴", 9000, "MC001");

        String menuId = addMenuDAO.getNewMenuId(vo);
        log.info("getNewMenuId(): {}", vo.getMenuName(), menuId);
        Assertions.assertNull(menuId);
    }

    @Test
    void hasMenuCheck_notExists() {
        String result = addMenuDAO.hasMenuCheck("xxxxxxx");
        log.info("hasMenuCheck(): {}", result);
        Assertions.assertNull(result);
    }

    @Test
    void hasMenuCheck_exists() {
        String result = addMenuDAO.hasMenuCheck("치즈김밥");
        log.info("hasMenuCheck(): {}", result);
        Assertions.assertNotNull(result);
    }

    @Test
    void getMenuList() {
        List<Menu> list = addMenuDAO.getMenuList("0000000000");
        log.info("getMenuList: {}", list);
        Assertions.assertNotNull(list);
    }

    @Test
    void getMenuDetail() {
        List<Menu> detail = addMenuDAO.getMenuDetail("1");
        log.info("getMenuDetail: {}", detail);
        Assertions.assertNotNull(detail);
    }

    @Test
    void addUsedMaterial() {
        String foodMaterialId = addFoodMaterialDAO.getFoodMaterialListAll("0000000000").get(0).getFoodMaterialId();
        String categoryId = addMenuDAO.getCategoryId("김밥류");
        String menuId = addMenuDAO.getNewMenuId(new AddMenu("MI001","치즈김밥", 4000, categoryId));

        Used used = new Used("U020",5, foodMaterialId, menuId);
        int result = addMenuDAO.addUsedMaterial(used);
        log.info("addUsedMaterial() 결과: {}", foodMaterialId, menuId, result);
        Assertions.assertTrue(result > 0);
    }

    @Test
    void deleteUsedMaterial() {
        int result = addMenuDAO.deleteUsedMaterial("99999");
        log.info("deleteUsedMaterial(): {}", result);
        Assertions.assertTrue(result >= 0);
    }

    @Test
    void updateFoodMaterialAfterSale() {
        Map<String, Object> params = new HashMap<>();
        params.put("menuId", "1");
        params.put("saleCount", 1);
        params.put("bId", "0000000000");

        int result = addMenuDAO.updateFoodMaterialAfterSale(params);
        log.info("updateFoodMaterialAfterSale(): {}", result);
        Assertions.assertTrue(result >= 0);
    }
}
