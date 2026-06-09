package com.oopsw.kostaerpserver.dto.addmenu;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AddMenuCategoryResponse {
    private String result;
    private String message;
    private String menuCategoryId;
    private String menuCategory;

    public static AddMenuCategoryResponse success(String menuCategoryId, String menuCategory) {
        return AddMenuCategoryResponse.builder()
                .result("success")
                .menuCategoryId(menuCategoryId)
                .menuCategory(menuCategory)
                .build();
    }

    public static AddMenuCategoryResponse fail(String message) {
        return AddMenuCategoryResponse.builder()
                .result("fail")
                .message(message)
                .build();
    }
}
