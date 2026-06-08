package com.oopsw.kostaerpserver.dto.foodmaterial;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class FoodMaterialDeleteResponse {

    private String message;
    private String foodMaterialId;
}