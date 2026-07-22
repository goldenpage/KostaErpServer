package com.oopsw.kostaerpserver.dto.menu;

import com.oopsw.kostaerpserver.vo.Menu;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MenuMaterialResponse {
    private String foodMaterialId;
    private String foodMaterialName;
    private int usedCount;
    private int foodMaterialPrice;
    private int totalWeight;
    private int usedPrice;

    public static MenuMaterialResponse from(Menu menu) {
        return MenuMaterialResponse.builder()
                .foodMaterialId(menu.getFoodMaterialId())
                .foodMaterialName(menu.getFoodMaterialName())
                .usedCount(menu.getUsedCount())
                .foodMaterialPrice(menu.getFoodMaterialPrice())
                .totalWeight(menu.getTotalWeight())
                .usedPrice(menu.getUsedPrice())
                .build();
    }
}