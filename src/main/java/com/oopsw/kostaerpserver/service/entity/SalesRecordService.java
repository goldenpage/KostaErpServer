package com.oopsw.kostaerpserver.service.entity;

import com.oopsw.kostaerpserver.dto.salesrecord.SalesRecordResponse;
import org.springframework.data.domain.Page;

import java.util.List;

public interface SalesRecordService {
    Page <SalesRecordResponse> getSalesList(
            int page,
            int size
    );

    void addSale(
            String menuId,
            int saleMenuCount,
            String bId,
            String payment
    );

    List<SalesRecordResponse> getSalesByDate(
            String startDate,
            String endDate
    );

    void deleteSale(String saleId);
    SalesRecordResponse getSalesById(String id);
    void updateSale(String salesId, String menuId, int saleMenuCount, String payment);

    int getTotalRevenue();
}
