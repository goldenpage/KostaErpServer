package com.oopsw.kostaerpserver.service;

import com.oopsw.kostaerpserver.dto.menu.MenuDetailResponse;
import com.oopsw.kostaerpserver.dto.menu.MenuListRequest;
import com.oopsw.kostaerpserver.dto.menu.MenuListResponse;
import com.oopsw.kostaerpserver.dto.menu.MenuMaterialListResponse;
import com.oopsw.kostaerpserver.dto.menu.MenuMaterialResponse;
import com.oopsw.kostaerpserver.dto.menu.MenuResponse;
import com.oopsw.kostaerpserver.dto.stocknotice.StockNoticeResponse;
import com.oopsw.kostaerpserver.repository.MenuDAO;
import com.oopsw.kostaerpserver.service.Interface.MenuService;
import com.oopsw.kostaerpserver.service.Interface.StockNoticeSettingService;
import com.oopsw.kostaerpserver.service.entity.OutOfStockNoticeServiceImpl;
import com.oopsw.kostaerpserver.vo.Menu;
import com.oopsw.kostaerpserver.vo.entity.OutOfStockNoticeVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MenuServiceImpl implements MenuService {

    private final MenuDAO menuDAO;
    private final OutOfStockNoticeServiceImpl outOfStockNoticeServiceImpl;
    private final StockNoticeSettingService stockNoticeSettingService;

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
    public void saleMenu(String menuId, int saleCount, String bId, String payment) {
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

        generateLowStockNotices(menuId, bId);

        String lastId = menuDAO.getLastRevenueId();
        String revenueId;

        if (lastId == null) {
            revenueId = "RV001";
        } else {
            // "RV" 뒤의 숫자 부분만 추출하여 +1
            int num = Integer.parseInt(lastId.substring(2)) + 1;
            revenueId = String.format("RV%03d", num); // 3자리 숫자로 포맷팅
        }
        int revenueResult = menuDAO.insertRevenue(revenueId, bId, payment);
        int insertSaleRecord = menuDAO.insertSaleRecord(menuId, saleCount, revenueId);
        System.out.println("SALES 저장 결과: " + insertSaleRecord);
        if (insertSaleRecord == 0) {
            throw new RuntimeException("판매 기록 저장에 실패했습니다.");
        }
    }

    @Override
    public MenuListResponse getMenuListResponse(MenuListRequest request) {
        String bId = request.getBId();

        if (bId == null || bId.isBlank()) {
            bId = "0000000000";
        }

        List<MenuResponse> menuList = getMenuList(bId).stream()
                .map(MenuResponse::from)
                .toList();

        return MenuListResponse.builder()
                .menuList(menuList)
                .totalCount(menuList.size())
                .bId(bId)
                .build();
    }

    @Override
    public MenuDetailResponse getMenuDetailResponse(String menuId) {
        List<Menu> detailList = getMenuDetail(menuId);

        if (detailList.isEmpty()) {
            throw new RuntimeException("메뉴 정보를 찾을 수 없습니다.");
        }
        return MenuDetailResponse.from(detailList.get(0));
    }

    @Override
    public MenuMaterialListResponse getMenuMaterialListResponse(String menuId) {
        List<MenuMaterialResponse> materialList = getMenuDetail(menuId).stream()
                .map(MenuMaterialResponse::from)
                .toList();

        return MenuMaterialListResponse.builder()
                .menuId(menuId)
                .materialList(materialList)
                .totalCount(materialList.size())
                .build();
    }


    @Override
    public List<Menu> getLowStockMaterialList(String menuId, String bId) {
        return menuDAO.getLowStockMaterialList(menuId, bId);
    }

    private void generateLowStockNotices(String menuId, String bId) {
        List<Menu> lowStockList = getLowStockMaterialList(menuId, bId);

        for (Menu material : lowStockList) {
            outOfStockNoticeServiceImpl.addOutOfStockNotice(
                    OutOfStockNoticeVO.builder()
                            .noticeContent(material.getFoodMaterialName() + " 재고 부족")
                            .foodMaterialName(material.getFoodMaterialName())
                            .remainStockAmount(material.getFoodMaterialCountAll())
                            .bId(bId)
                            .build()
            );
        }
    }

    @Override
    @Transactional
    public void deleteMenu(String menuId) {
        int result = menuDAO.deleteMenu(menuId);

        if (result == 0) {
            throw new RuntimeException("삭제 실패");
        }
    }
}