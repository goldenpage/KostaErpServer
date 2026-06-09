package com.oopsw.kostaerpserver.dto.menu;

import com.oopsw.kostaerpserver.vo.Menu;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MenuResponse {
    private String menuId;
    private String menuName;
    private int menuPrice;
    private String menuCategory;

    public static MenuResponse from(Menu menu) {
        return MenuResponse.builder()
                .menuId(menu.getMenuId())
                .menuName(menu.getMenuName())
                .menuPrice(menu.getMenuPrice())
                .menuCategory(menu.getMenuCategory())
                .build();
    }
}