package com.oopsw.kostaerpserver.service.entity.purchase;

import com.oopsw.kostaerpserver.vo.AddFoodMaterial;
import com.oopsw.kostaerpserver.vo.entity.PurchaseVO;

public class PurchaseConvert {
    public static PurchaseVO from(AddFoodMaterial addFoodMaterial) {
        return PurchaseVO.builder()
                .foodMaterialName(addFoodMaterial.getFoodMaterialName())
                .foodMaterialCount(addFoodMaterial.getFoodMaterialCount())
                .foodMaterialWeight(addFoodMaterial.getFoodMaterialCountAll())
                .foodMaterialPrice(addFoodMaterial.getFoodMaterialPrice())
                .vender(addFoodMaterial.getVender())
                .incomeDate(addFoodMaterial.getIncomeDate())
                .expirationDate(addFoodMaterial.getExpirationDate())
                .bId(addFoodMaterial.getBId())
                .build();
    }
}
