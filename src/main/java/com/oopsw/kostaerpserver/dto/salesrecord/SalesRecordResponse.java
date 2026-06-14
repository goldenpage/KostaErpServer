package com.oopsw.kostaerpserver.dto.salesrecord;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SalesRecordResponse {
    private String saleId;
    private String revenueId;

    private String saleDate;
    private String menuName;
    private String category;

    private int qty;
    private int price;
    private int totalPrice;

    private String paymentMethod;
}
