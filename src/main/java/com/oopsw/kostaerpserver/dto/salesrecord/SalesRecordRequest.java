package com.oopsw.kostaerpserver.dto.salesrecord;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SalesRecordRequest {
    private String menuId;
    private int saleMenuCount;
    private String bId;
    private String payment;
}
