package com.oopsw.kostaerpserver.dto.sales;

import lombok.Getter;

@Getter
public class SalesMenuResponse {
    private final String menuId;
    private final String menuName;
    private final String menuCategory;
    private final int menuPrice;

    public SalesMenuResponse(
            String menuId,
            String menuName,
            String menuCategory,
            int menuPrice
    ) {
        this.menuId = menuId;
        this.menuName = menuName;
        this.menuCategory = menuCategory;
        this.menuPrice = menuPrice;
    }
}
