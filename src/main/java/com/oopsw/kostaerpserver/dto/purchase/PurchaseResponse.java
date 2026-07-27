package com.oopsw.kostaerpserver.dto.purchase;

import com.oopsw.kostaerpserver.repository.entity.outofstocknotice.OutOfStockNotice;
import com.oopsw.kostaerpserver.repository.entity.purchase.Purchase;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class PurchaseResponse {
    private final int purchaseId;
    private final String foodMaterialName;
    private final int foodMaterialCount;
    private final int foodMaterialWeight;
    private final int totalWeight;
    private final int foodMaterialPrice;
    private final int totalPrice;
    private final String vender;
    private final String incomeDate;
    private final String expirationDate;

    public PurchaseResponse(Purchase entity) {
        this.purchaseId = entity.getPurchaseId();
        this.foodMaterialName = entity.getFoodMaterialName();
        this.foodMaterialCount = entity.getFoodMaterialCount();
        this.foodMaterialWeight = entity.getFoodMaterialWeight();
        this.totalWeight = entity.getTotalWeight();
        this.foodMaterialPrice = entity.getFoodMaterialPrice();
        this.totalPrice = entity.getTotalPrice();
        this.vender = entity.getVender();
        this.incomeDate = entity.getIncomeDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        this.expirationDate = entity.getExpirationDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }

    public static List<PurchaseResponse> fromList(List<Purchase> list) {
        return list.stream().map(PurchaseResponse::new).collect(Collectors.toList());
    }
}
