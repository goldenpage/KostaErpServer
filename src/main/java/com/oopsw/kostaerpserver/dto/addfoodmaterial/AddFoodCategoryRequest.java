package com.oopsw.kostaerpserver.dto.addfoodmaterial;

import com.oopsw.kostaerpserver.vo.FoodCategory;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AddFoodCategoryRequest {
    private String foodCategory;

    public FoodCategory toVO() {
        return FoodCategory.builder()
                .foodCategory(this.foodCategory)
                .build();
    }
}
