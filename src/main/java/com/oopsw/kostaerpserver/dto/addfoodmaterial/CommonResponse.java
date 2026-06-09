package com.oopsw.kostaerpserver.dto.addfoodmaterial;

import lombok.Getter;

@Getter
public class CommonResponse {
    private final String result;
    private final String message;

    private CommonResponse(String result, String message) {
        this.result = result;
        this.message = message;
    }

    public static CommonResponse success(String message) {
        return new CommonResponse("success", message);
    }

    public static CommonResponse fail(String message) {
        return new CommonResponse("fail", message);
    }
}
