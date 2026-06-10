package com.oopsw.kostaerpserver.vo.entity;

import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
public class OutOfStockNoticeVO {
    private int noticeId;
    private String noticeDate;
    private String noticeContent;
    private String foodMaterialName;
    private int remainStockAmount;
    private String bId;
    private String readYn;
}
