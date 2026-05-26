package com.oopsw.kostaerpserver.vo;

import lombok.Data;

@Data
public class Menu {
    private String menuId;
    private String menuName;
    private int menuPrice;
    private String menuCategory;
    private String foodMaterialId;
    private String foodMaterialName;
    private int usedCount;
    private int foodMaterialPrice;
    private int foodMaterialCountAll;
    private int usedPrice;
}