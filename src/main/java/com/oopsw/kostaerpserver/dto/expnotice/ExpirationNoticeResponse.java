package com.oopsw.kostaerpserver.dto.expnotice;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ExpirationNoticeResponse {
    private String foodMaterialId;
    private String foodMaterialName;
    private String expirationDate;
    private long remainDays;
    private String noticeContent;
}