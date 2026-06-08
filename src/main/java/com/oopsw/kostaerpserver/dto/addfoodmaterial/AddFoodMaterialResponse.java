package com.oopsw.kostaerpserver.dto.addfoodmaterial;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AddFoodMaterialResponse {
    private String result;
    private String message;

    public static AddFoodMaterialResponse success(int count) {
        return AddFoodMaterialResponse.builder()
                .result("success")
                .message(count + "개의 식자재 등록 완료")
                .build();
    }

    public static AddFoodMaterialResponse fail(String message) {
        return AddFoodMaterialResponse.builder()
                .result("fail")
                .message(message)
                .build();
    }
}
