package com.oopsw.kostaerpserver.vo;

import lombok.Data;

import java.time.LocalDate;

@Data
public class FoodMaterial {
    private String foodMaterialId;
    private String foodMaterialName;
    private String foodCategory;
    private int foodMaterialCount;
    private int foodMaterialCountAll;
    private int foodMaterialPrice;
    private String foodMaterialType;
    private String vender;
    private LocalDate incomeDate;
    private LocalDate expirationDate;
    private String bId;
}
