package com.oopsw.kostaerpserver.dto.addfoodmaterial;

import com.oopsw.kostaerpserver.vo.FoodCategory;
import lombok.*;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GetFoodCategoryListResponse {
    private String foodCategoryId;
    private String foodCategory;

    public static GetFoodCategoryListResponse from(FoodCategory vo) {
        return GetFoodCategoryListResponse.builder()
                .foodCategoryId(vo.getFoodCategoryId())
                .foodCategory(vo.getFoodCategory())
                .build();
    }

    public static List<GetFoodCategoryListResponse> fromList(List<FoodCategory> voList) {
        return voList.stream()
                .map(GetFoodCategoryListResponse::from)
                .collect(Collectors.toList());
    }
}
