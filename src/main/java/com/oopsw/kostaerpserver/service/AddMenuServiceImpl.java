package com.oopsw.kostaerpserver.service;

import com.oopsw.kostaerpserver.repository.AddMenuDAO;
import com.oopsw.kostaerpserver.vo.AddMenu;
import com.oopsw.kostaerpserver.vo.Menu;
import com.oopsw.kostaerpserver.vo.MenuCategory;
import com.oopsw.kostaerpserver.vo.Used;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AddMenuServiceImpl implements AddMenuService {

    private final AddMenuDAO addMenuDAO;

    // 1. 메뉴 입력
    @Override
    public int addMenu(AddMenu addMenu) {
        return addMenuDAO.addMenu(addMenu);
    }

    // 2. 메뉴 ID 조회
    public String getNewMenuId(AddMenu addMenu) {
        return addMenuDAO.getNewMenuId(addMenu);
    }

    // 3. 메뉴 카테고리 존재 여부 확인
    public int checkMenuCategoryExists(MenuCategory menuCategory) {
        return addMenuDAO.checkMenuCategoryExists(menuCategory);
    }

    // 4. 메뉴 카테고리 추가
    public int addMenuCategory(MenuCategory menuCategory) {
        return addMenuDAO.addMenuCategory(menuCategory);
    }

    // 5. 메뉴 카테고리 삭제
    public int deleteMenuCategory(String menuCategory) {
        return addMenuDAO.deleteMenuCategory(menuCategory);
    }

    // 6. 메뉴-식자재 연결 INSERT
    public int addUsedMaterial(Used used) {
        return addMenuDAO.addUsedMaterial(used);
    }

    // 7. 사용중인 식자재 삭제
    public int deleteUsedMaterial(String usedMaterialId) {
        return addMenuDAO.deleteUsedMaterial(usedMaterialId);
    }

    // 8. 판매 후 식자재 재고 차감
    public int updateFoodMaterialAfterSale(Map<String, Object> params) {
        return addMenuDAO.updateFoodMaterialAfterSale(params);
    }

    // 9. 메뉴 카테고리 목록 조회
    @Override
    public List<MenuCategory> getMenuCategoryList(String bId) {
        return addMenuDAO.getMenuCategoryList(bId);
    }

    // 10. 해당 카테고리에 메뉴 존재 여부
    @Override
    public int hasMenuByCategory(String menuCategory) {
        return addMenuDAO.hasMenuByCategory(menuCategory);
    }

    // 11. 메뉴명 중복 확인
    @Override
    public String hasMenuCheck(String menuName) {
        return addMenuDAO.hasMenuCheck(menuName);
    }

    // 12. 카테고리명으로 카테고리 ID 조회
    @Override
    public String getCategoryId(String menuCategory) {
        return addMenuDAO.getCategoryId(menuCategory);
    }
}
