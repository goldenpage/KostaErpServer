package com.oopsw.kostaerpserver.dto.outofstock;

import com.oopsw.kostaerpserver.repository.entity.outofstocknotice.OutOfStockNotice;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class OutOfStockNoticeResponse {
    private final int noticeId;
    private final String foodMaterialName;
    private final String noticeContent;
    private final int remainStockAmount;
    private final LocalDateTime noticeDate;
    private final String readYn;

    public OutOfStockNoticeResponse(OutOfStockNotice entity) {
        this.noticeId = entity.getNoticeId();
        this.foodMaterialName = entity.getFoodMaterialName();
        this.noticeContent = entity.getNoticeContent();
        this.remainStockAmount = entity.getRemainStockAmount();
        this.noticeDate = entity.getNoticeDate();
        this.readYn = entity.getReadYn();
    }
}
