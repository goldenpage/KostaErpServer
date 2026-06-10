package com.oopsw.kostaerpserver.dto;

import com.oopsw.kostaerpserver.repository.entity.outofstocknotice.OutOfStockNotice;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class OutOfStockNoticeResponse {
    private int noticeId;
    private String foodMaterialName;
    private String noticeContent;
    private int remainStockAmount;
    private LocalDateTime noticeDate;
    private String readYn;

    // Entity → DTO 변환은 DTO 생성자에서 처리
    public OutOfStockNoticeResponse(OutOfStockNotice entity) {
        this.noticeId          = entity.getNoticeId();
        this.foodMaterialName  = entity.getFoodMaterialName();
        this.noticeContent     = entity.getNoticeContent();
        this.remainStockAmount = entity.getRemainStockAmount();
        this.noticeDate        = entity.getNoticeDate();
        this.readYn            = entity.getReadYn();
    }
}
