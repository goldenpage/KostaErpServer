package com.oopsw.kostaerpserver.dto.addmenu;

import com.oopsw.kostaerpserver.vo.MenuCategory;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AddMenuCategoryRequest {
    private String menuCategory;

    public MenuCategory toVO(String bId) {
        return MenuCategory.builder().menuCategory(this.menuCategory).bId(bId).build();
    }
}
