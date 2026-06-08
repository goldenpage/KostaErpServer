package com.oopsw.kostaerpserver.dto.menu;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SaleResponse {
    private String message;
    private String menuId;
    private int saleCount;
}