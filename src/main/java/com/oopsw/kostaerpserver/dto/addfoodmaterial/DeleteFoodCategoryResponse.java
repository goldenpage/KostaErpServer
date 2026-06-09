package com.oopsw.kostaerpserver.dto.addfoodmaterial;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DeleteFoodCategoryResponse {
    private String result;
    private String message;

    public static DeleteFoodCategoryResponse success(String foodCategory) {
        return DeleteFoodCategoryResponse.builder()
                .result("success")
                .message(foodCategory + "가 삭제되었습니다.")
                .build();
    }

    public static DeleteFoodCategoryResponse fail(String message) {
        return DeleteFoodCategoryResponse.builder()
                .result("fail")
                .message(message)
                .build();
    }
}
