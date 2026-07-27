package com.oopsw.kostaerpserver.dto.foodmaterial;

import com.oopsw.kostaerpserver.vo.FoodMaterial;
import java.util.Date;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class FoodMaterialResponse {

    private String foodMaterialId;
    private String foodMaterialName;
    private String foodCategory;
    private int foodMaterialCount;
    private int foodMaterialWeight;
    private int totalWeight;
    private int foodMaterialPrice;
    private String vender;
    private Date incomeDate;
    private Date expirationDate;
    private String foodMaterialType;

    public static FoodMaterialResponse from(FoodMaterial food) {
        return FoodMaterialResponse.builder()
                .foodMaterialId(food.getFoodMaterialId())
                .foodMaterialName(food.getFoodMaterialName())
                .foodCategory(food.getFoodCategory())
                .foodMaterialCount(food.getFoodMaterialCount())
                .foodMaterialWeight(food.getFoodMaterialWeight())
                .totalWeight(food.getTotalWeight())
                .foodMaterialPrice(food.getFoodMaterialPrice())
                .vender(food.getVender())
                .incomeDate(food.getIncomeDate())
                .expirationDate(food.getExpirationDate())
                .foodMaterialType(food.getFoodMaterialType())
                .build();
    }
}