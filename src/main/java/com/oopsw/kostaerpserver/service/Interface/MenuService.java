package com.oopsw.kostaerpserver.service.Interface;

import com.oopsw.kostaerpserver.vo.Menu;

import java.util.List;

public interface MenuService {

    List<Menu> getMenuList(String bId);
    List<Menu> getMenuDetail(String menuId);
    void saleMenu(String menuId, int saleCount, String bId);
}