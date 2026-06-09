package com.oopsw.kostaerpserver.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class NoticeListResponse {
    private String noticeId;
    private String disposalId;
    private String readYn;

    private String foodMaterialName;
    private String foodCategory;
    private int disposalCountAll;
    private LocalDate expirationDate;
    private String foodMaterialType;
}
