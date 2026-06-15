package com.oopsw.kostaerpserver.service.entity.salesrecord;

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

    Page<SalesRecordResponse> searchSales(
            String startDate,
            String endDate,
            String category,
            String menuName,
            String payment,
            int page,
            int size
    );

    void deleteSale(String saleId);
    SalesRecordResponse getSalesById(String id);
    void updateSale(String salesId, String menuId, int saleMenuCount, String payment);

    int getTotalRevenue();

    List<SalesRecordResponse> getSalesByDate(String date, String date1);
}
