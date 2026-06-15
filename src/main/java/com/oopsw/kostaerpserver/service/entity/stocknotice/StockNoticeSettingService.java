package com.oopsw.kostaerpserver.service.entity.stocknotice;

import com.oopsw.kostaerpserver.dto.stocknotice.StockNoticeRequest;
import com.oopsw.kostaerpserver.dto.stocknotice.StockNoticeResponse;

public interface StockNoticeSettingService {
    StockNoticeResponse getStockNoticeSetting(String bId);
    StockNoticeResponse updateStockNoticeSetting(String bId, StockNoticeRequest request);
}