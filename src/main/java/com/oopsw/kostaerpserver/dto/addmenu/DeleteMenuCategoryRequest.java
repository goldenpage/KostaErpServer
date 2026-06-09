package com.oopsw.kostaerpserver.dto.addmenu;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DeleteMenuCategoryRequest {
    private String menuCategory;
}
