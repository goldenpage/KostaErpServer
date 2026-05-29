package com.oopsw.kostaerpserver.service;

import com.oopsw.kostaerpserver.repository.MenuDAO;
import com.oopsw.kostaerpserver.vo.Menu;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MenuServiceImpl implements MenuService {

    private final MenuDAO menuDAO;

    @Override
    public List<Menu> getMenuList(String bId) {
        return menuDAO.getMenuList(bId);
    }

    @Override
    public List<Menu> getMenuDetail(String menuId) {
        return menuDAO.getMenuDetail(menuId);
    }

    @Override
    @Transactional
    public void saleMenu(String menuId, int saleCount, String bId) {
        if (saleCount < 1) {
            throw new RuntimeException("판매 수량은 1 이상이어야 합니다.");
        }

        int lackCount = menuDAO.getLackMaterialCount(menuId, saleCount);
        if (lackCount > 0) {
            throw new RuntimeException("재고가 부족합니다.");
        }

        int result = menuDAO.updateFoodMaterialAfterSale(menuId, saleCount, bId);
        if (result == 0) {
            throw new RuntimeException("판매 처리할 식자재가 없습니다.");
        }
    }
}