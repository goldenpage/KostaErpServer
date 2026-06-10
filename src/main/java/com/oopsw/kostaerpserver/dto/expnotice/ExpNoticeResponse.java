package com.oopsw.kostaerpserver.dto.expnotice;

import com.oopsw.kostaerpserver.repository.entity.expdate.ExpNotice;
import lombok.Getter;

@Getter
public class ExpNoticeResponse {
    private String bId;
    private boolean expAlert;
    private int expDays;

    public ExpNoticeResponse(ExpNotice expNotice) {
        this.bId = expNotice.getBId();
        this.expAlert = expNotice.isExpAlert();
        this.expDays = expNotice.getExpDays();
    }
}