package com.oopsw.kostaerpserver.service.Interface;

import com.oopsw.kostaerpserver.vo.Menu;

import com.oopsw.kostaerpserver.dto.menu.MenuDetailResponse;
import com.oopsw.kostaerpserver.dto.menu.MenuListRequest;
import com.oopsw.kostaerpserver.dto.menu.MenuListResponse;
import com.oopsw.kostaerpserver.dto.menu.MenuMaterialListResponse;
import java.util.List;

public interface MenuService {

    List<Menu> getMenuList(String bId);
    List<Menu> getMenuDetail(String menuId);
    void saleMenu(String menuId, int saleCount, String bId, String payment);
    MenuListResponse getMenuListResponse(MenuListRequest request);
    MenuDetailResponse getMenuDetailResponse(String menuId);
    MenuMaterialListResponse getMenuMaterialListResponse(String menuId);
    List<Menu> getLowStockMaterialList(String menuId, String bId);
    void deleteMenu(String menuId);
}