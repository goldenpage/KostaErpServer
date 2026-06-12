package com.oopsw.kostaerpserver.vo.entity;

import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
public class PurchaseVO {
    private int purchaseId;
    private String foodMaterialName;
    private int foodMaterialCount;
    private int foodMaterialWeight;
    private int totalWeight;
    private int foodMaterialPrice;
    private int totalPrice;
    private String vender;
    private String incomeDate;
    private String expirationDate;
}
