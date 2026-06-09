package com.oopsw.kostaerpserver.dto.menu;

import com.oopsw.kostaerpserver.vo.Menu;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MenuDetailResponse {
    private String menuId;
    private String menuName;
    private int menuPrice;

    public static MenuDetailResponse from(Menu menu) {
        return MenuDetailResponse.builder()
                .menuId(menu.getMenuId())
                .menuName(menu.getMenuName())
                .menuPrice(menu.getMenuPrice())
                .build();
    }
}