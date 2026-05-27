package com.oopsw.kostaerpserver.repository;

import com.oopsw.kostaerpserver.vo.Menu;
import com.oopsw.kostaerpserver.vo.MenuCategory;
import com.oopsw.kostaerpserver.vo.Used;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SpringBootTest
@Transactional
public class AddMenuDAOTest {
    @Autowired
    AddMenuDAO addMenuDAO;

    @Autowired
    AddFoodMaterialDAO addFoodMaterialDAO;

    @Test
    void getMenuCategoryList() {
        List<MenuCategory> list = addMenuDAO.getMenuCategoryList("0000000000");
        System.out.println(list);
        Assertions.assertNotNull(list);
    }

    @Test
    void checkMenuCategoryExists() {
        MenuCategory vo = new MenuCategory("김밥류", "0000000000");
        int count = addMenuDAO.checkMenuCategoryExists(vo);
        System.out.println(count);
        Assertions.assertTrue(count > 0);
    }

    @Test
    void addMenuCategory() {
        MenuCategory vo = new MenuCategory("테스트메뉴카테고리", "0000000000");
        int result = addMenuDAO.addMenuCategory(vo);
        System.out.println(result);
        Assertions.assertTrue(result == 1);
    }

    @Test
    void deleteMenuCategory() {
        int result = addMenuDAO.deleteMenuCategory("떡볶이류");
        System.out.println(result);
        Assertions.assertTrue(result == 1);
    }

    @Test
    void getCategoryId() {
        String categoryId = addMenuDAO.getCategoryId("김밥류");
        System.out.println(categoryId);
        Assertions.assertNotNull(categoryId);
    }

    @Test
    void hasMenuByCategory() {
        int count = addMenuDAO.hasMenuByCategory("한식");
        System.out.println(count);
        Assertions.assertTrue(count >= 0);
    }

    @Test
    void addMenu() {
        String categoryId = addMenuDAO.getCategoryId("한식");
        Menu menu = new Menu("테스트메뉴", 9000, categoryId);

        int result = addMenuDAO.addMenu(menu);
        System.out.println(result);
        Assertions.assertTrue(result == 1);
    }

    @Test
    void getNewMenuId() {
        String categoryId = addMenuDAO.getCategoryId("김밥류");
        Menu menu = new Menu("테스트메뉴", 9000, categoryId);

        String menuId = addMenuDAO.getNewMenuId(menu);
        System.out.println(menuId);
        Assertions.assertNotNull(menuId);
    }

    @Test
    void hasMenuCheck_notExists() {
        String result = addMenuDAO.hasMenuCheck("xxxxxxx");
        System.out.println(result);
        Assertions.assertNull(result);
    }

    @Test
    void hasMenuCheck_exists() {
        String result = addMenuDAO.hasMenuCheck("치즈김밥");
        System.out.println(result);
        Assertions.assertNotNull(result);
    }

    @Test
    void getMenuList() {
        List<Menu> list = addMenuDAO.getMenuList("0000000000");
        System.out.println(list);
        Assertions.assertNotNull(list);
    }

    @Test
    void getMenuDetail() {
        List<Menu> detail = addMenuDAO.getMenuDetail("1");
        System.out.println(detail);
        Assertions.assertNotNull(detail);
    }

    @Test
    void addUsedMaterial() {
        String foodMaterialId = addFoodMaterialDAO.getFoodMaterialListAll("0000000000").get(0).getFoodMaterialId();
        String menuId = addMenuDAO.getNewMenuId(new Menu("테스트메뉴", 9000, addMenuDAO.getCategoryId("한식")));

        Used used = new Used(5, foodMaterialId, menuId);
        int result = addMenuDAO.addUsedMaterial(used);
        System.out.println(result);
        Assertions.assertTrue(result == 1);
    }

    @Test
    void deleteUsedMaterial() {
        int result = addMenuDAO.deleteUsedMaterial("99999");
        System.out.println(result);
        Assertions.assertTrue(result >= 0);
    }

    @Test
    void updateFoodMaterialAfterSale() {
        Map<String, Object> params = new HashMap<>();
        params.put("menuId", "1");
        params.put("saleCount", 1);
        params.put("bId", "0000000000");

        int result = addMenuDAO.updateFoodMaterialAfterSale(params);
        System.out.println(result);
        Assertions.assertTrue(result >= 0);
    }
}
