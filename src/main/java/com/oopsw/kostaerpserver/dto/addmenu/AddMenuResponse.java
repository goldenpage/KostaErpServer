package com.oopsw.kostaerpserver.dto.addmenu;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AddMenuResponse {
    private String result;
    private String message;

    public static AddMenuResponse success(int count) {
        return AddMenuResponse.builder()
                .result("success")
                .message(count + "개의 메뉴 등록 성공")
                .build();
    }

    public static AddMenuResponse fail(String message) {
        return AddMenuResponse.builder()
                .result("fail")
                .message(message)
                .build();
    }
}
