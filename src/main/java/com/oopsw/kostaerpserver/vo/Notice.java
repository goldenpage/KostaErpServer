package com.oopsw.kostaerpserver.vo;

import lombok.Data;

import java.time.LocalDate;

@Data
public class Notice {
    private String noticeId;
    private String disposalId;
    private LocalDate noticeDate;
    private String readYn;

    private String foodMaterialName;
    private String foodCategory;
    private int disposalCountAll;
    private LocalDate expirationDate;
    private String foodMaterialType;
}
