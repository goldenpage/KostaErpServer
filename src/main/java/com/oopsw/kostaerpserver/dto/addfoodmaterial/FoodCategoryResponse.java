package com.oopsw.kostaerpserver.dto.addfoodmaterial;

import lombok.Getter;

@Getter
public class FoodCategoryResponse {
    private final String result;
    private final String message;
    private final String foodCategoryId;
    private final String foodCategory;

    private FoodCategoryResponse(String foodCategoryId, String foodCategory) {
        this.result = "success";
        this.message = null;
        this.foodCategoryId = foodCategoryId;
        this.foodCategory = foodCategory;
    }

    private FoodCategoryResponse(String message) {
        this.result = "fail";
        this.message = message;
        this.foodCategoryId = null;
        this.foodCategory = null;
    }

    public static FoodCategoryResponse success(String foodCategoryId, String foodCategory) {
        return new FoodCategoryResponse(foodCategoryId, foodCategory);
    }

    public static FoodCategoryResponse fail(String message) {
        return new FoodCategoryResponse(message);
    }
}
