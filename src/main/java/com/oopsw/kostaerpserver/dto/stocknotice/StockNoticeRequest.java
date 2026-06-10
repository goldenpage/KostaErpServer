package com.oopsw.kostaerpserver.dto.stocknotice;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class StockNoticeRequest {
    private boolean foodmAlert;
    private int foodmLimit;
}