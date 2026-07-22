package com.oopsw.kostaerpserver.dto.addmenu;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AddMenuRequest {
    private List<String> menuName;
    private List<String> menuPrice;
    private List<String> menuCategoryId;
    private List<String> menuIngredientCount;
    private List<String> foodMaterialId;
    private List<String> usedCount;
}
