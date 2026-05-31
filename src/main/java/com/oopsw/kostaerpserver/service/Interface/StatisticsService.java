package com.oopsw.kostaerpserver.service.Interface;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Service;

@Service
public interface StatisticsService {

    List<Map<String, Object>> getMonthlyFoodMaterialExpenseRank(
        String bId,
        LocalDate startDate,
        LocalDate endDate

    );

    Long getTotalExpense(
        String bId,
        LocalDate startDate,
        LocalDate endDate
    ) ;

    List<Map<String, Object>> getMonthlyExpenseRankChart(
        String bId,
        LocalDate startDate,
        LocalDate endDate
    ) ;

    List<Map<String, Object>> getSalesHistory(
        String bId,
        LocalDate startDate,
        LocalDate endDate
    ) ;

    Long getTotalSales(
        String bId,
        LocalDate startDate,
        LocalDate endDate

    ) ;

    List<Map<String, Object>> getMenuSalesRank(
        String bId,
        LocalDate startDate,
        LocalDate endDate

    ) ;

    Double getDisposalRate(
        String bId,
        LocalDate startDate,
        LocalDate endDate

    ) ;

    Long getTotalDisposalPrice(
        String bId,
        LocalDate startDate,
        LocalDate endDate

    ) ;

    List<Map<String, Object>> getTopDisposalMaterials(
        String bId,
        LocalDate startDate,
        LocalDate endDate

    ) ;

    List<Map<String, Object>> getDisposalReasonRatio(
        String bId,
        LocalDate startDate,
        LocalDate endDate

    ) ;

    List<Map<String, Object>> getDailyDisposalChart(
        String bId,
        LocalDate startDate,
        LocalDate endDate

    ) ;
}
