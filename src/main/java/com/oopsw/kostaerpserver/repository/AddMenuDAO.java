package com.oopsw.kostaerpserver.repository;

import com.oopsw.kostaerpserver.vo.AddMenu;
import com.oopsw.kostaerpserver.vo.Menu;
import com.oopsw.kostaerpserver.vo.MenuCategory;
import com.oopsw.kostaerpserver.vo.Used;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface AddMenuDAO {
    // 1. 메뉴 입력
    int addMenu(AddMenu addmenu);

    // 2. 메뉴 ID 조회
    String getNewMenuId(AddMenu addmenu);

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

    // 8. 메뉴 목록 조회
    List<Menu> getMenuList(String bId);

    // 9. 메뉴 상세 조회
    List<Menu> getMenuDetail(String menuId);

    // 10. 판매 후 식자재 재고 차감
    // params: menuId, saleCount, bId
    int updateFoodMaterialAfterSale(Map<String, Object> params);

    // 11. 메뉴 카테고리 목록 조회
    List<MenuCategory> getMenuCategoryList(String bId);

    // 12. 해당 카테고리에 메뉴 존재 여부
    int hasMenuByCategory(String menuCategory);

    // 13. 메뉴명 중복 확인
    String hasMenuCheck(String menuName);

    // 14. 카테고리명으로 카테고리 ID 조회
    String getCategoryId(String menuCategory);
}
