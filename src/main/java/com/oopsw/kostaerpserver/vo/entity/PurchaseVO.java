package com.oopsw.kostaerpserver.vo.entity;

import lombok.*;

import java.time.LocalDateTime;

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
    private LocalDateTime incomeDate;
    private LocalDateTime expirationDate;
    private String bId;
}
