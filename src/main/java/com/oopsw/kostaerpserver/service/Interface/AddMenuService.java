package com.oopsw.kostaerpserver.service.Interface;

import com.oopsw.kostaerpserver.vo.AddMenu;
import com.oopsw.kostaerpserver.vo.MenuCategory;
import com.oopsw.kostaerpserver.vo.Used;

import java.util.List;
import java.util.Map;

public interface AddMenuService {
    // 1. 메뉴 입력
    int addMenu(AddMenu addMenu);

    // 2. 메뉴 ID 조회
    String getNewMenuId(AddMenu addMenu);

    // 3. 메뉴 카테고리 존재 여부 확인
    int checkMenuCategoryExists(MenuCategory menuCategory);

    // 4. 메뉴 카테고리 추가
    int addMenuCategory(MenuCategory menuCategory);

    // 5. 메뉴 카테고리 삭제
    int deleteMenuCategory(String menuCategory);

    // 6. 메뉴-식자재 연결 INSERT (USED 테이블)
    int addUsedMaterial(Used used);

    // 7. 사용중인 식자재 삭제
    int deleteUsedMaterial(String usedMaterialId);

    // 8. 판매 후 식자재 재고 차감
    int updateFoodMaterialAfterSale(Map<String, Object> params);

    // 9. 메뉴 카테고리 목록 조회
    List<MenuCategory> getMenuCategoryList(String bId);

    // 10. 해당 카테고리에 메뉴 존재 여부
    int hasMenuByCategory(String menuCategory);

    // 11. 메뉴명 중복 확인
    String hasMenuCheck(String menuName);

    // 12. 카테고리명으로 카테고리 ID 조회
    String getCategoryId(String menuCategory);
}