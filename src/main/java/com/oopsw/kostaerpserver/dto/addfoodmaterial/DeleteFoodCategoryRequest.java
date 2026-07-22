package com.oopsw.kostaerpserver.dto.addfoodmaterial;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DeleteFoodCategoryRequest {
    private String foodCategory;
}
