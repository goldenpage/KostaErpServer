package com.oopsw.kostaerpserver.dto.addmenu;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DeleteMenuCategoryResponse {
    private String result;
    private String message;

    public static DeleteMenuCategoryResponse success(String menuCategory) {
        return DeleteMenuCategoryResponse.builder()
                .result("success")
                .message(menuCategory + "가 삭제되었습니다.")
                .build();
    }

    public static DeleteMenuCategoryResponse fail(String message) {
        return DeleteMenuCategoryResponse.builder()
                .result("fail")
                .message(message)
                .build();
    }
}
