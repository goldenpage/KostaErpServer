package com.oopsw.kostaerpserver.dto.stocknotice;

import com.oopsw.kostaerpserver.repository.entity.stocknotice.StockNoticeSetting;
import lombok.Getter;

@Getter
public class StockNoticeResponse {
    private String bId;
    private boolean foodmAlert;
    private int foodmLimit;

    public StockNoticeResponse(StockNoticeSetting setting) {
        this.bId = setting.getBId();
        this.foodmAlert = setting.isFoodmAlert();
        this.foodmLimit = setting.getFoodmLimit();
    }
}