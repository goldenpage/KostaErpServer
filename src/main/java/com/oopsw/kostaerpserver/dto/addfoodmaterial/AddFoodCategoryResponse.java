package com.oopsw.kostaerpserver.dto.addfoodmaterial;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AddFoodCategoryResponse {
    private String result;
    private String message;
    private String foodCategoryId;
    private String foodCategory;

    public static AddFoodCategoryResponse success(String foodCategoryId, String foodCategory) {
        return AddFoodCategoryResponse.builder()
                .result("success")
                .foodCategoryId(foodCategoryId)
                .foodCategory(foodCategory)
                .build();
    }

    public static AddFoodCategoryResponse fail(String message) {
        return AddFoodCategoryResponse.builder()
                .result("fail")
                .message(message)
                .build();
    }
}
