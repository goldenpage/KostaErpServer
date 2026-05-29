package com.oopsw.kostaerpserver.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

@Service
public interface StatisticsService {

    List<Map<String, Object>> getMonthlyFoodMaterialExpenseRank(
        String bId,
        @Param("startDate") LocalDate startDate,
        @Param("endDate") LocalDate endDate

    );

    Long getTotalExpense(
        String bId,
        @Param("startDate") LocalDate startDate,
        @Param("endDate") LocalDate endDate
    );

    List<Map<String, Object>> getMonthlyExpenseRankChart(
        String bId,
        @Param("startDate") LocalDate startDate,
        @Param("endDate") LocalDate endDate
    );

    List<Map<String, Object>> getSalesHistory(
        String bId,
        @Param("startDate") LocalDate startDate,
        @Param("endDate") LocalDate endDate
    );

    Long getTotalSales(
        String bId,
        @Param("startDate") LocalDate startDate,
        @Param("endDate") LocalDate endDate

    );

    List<Map<String, Object>> getMenuSalesRank(
        String bId,
        @Param("startDate") LocalDate startDate,
        @Param("endDate") LocalDate endDate

    );

    Double getDisposalRate(
        String bId,
        @Param("startDate") LocalDate startDate,
        @Param("endDate") LocalDate endDate

    );

    Long getTotalDisposalPrice(
        String bId,
        @Param("startDate") LocalDate startDate,
        @Param("endDate") LocalDate endDate

    );

    List<Map<String, Object>> getTopDisposalMaterials(
        String bId,
        @Param("startDate") LocalDate startDate,
        @Param("endDate") LocalDate endDate

    );

    List<Map<String, Object>> getDisposalReasonRatio(
        String bId,
        @Param("startDate") LocalDate startDate,
        @Param("endDate") LocalDate endDate

    );

    List<Map<String, Object>> getDailyDisposalChart(
        String bId,
        @Param("startDate") LocalDate startDate,
        @Param("endDate") LocalDate endDate

    );
}
