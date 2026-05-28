package com.oopsw.kostaerpserver.vo;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
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
