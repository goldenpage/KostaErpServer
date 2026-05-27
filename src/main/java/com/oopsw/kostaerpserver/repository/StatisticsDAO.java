package com.oopsw.kostaerpserver.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface StatisticsDAO {
    List<Map<String, Object>> getMonthlyFoodMaterialExpenseRank(

        @Param("bId") String bId,

        @Param("startDate") LocalDate startDate,

        @Param("endDate") LocalDate endDate

    );

    Long getTotalExpense(
        @Param("bId") String bId,
        @Param("startDate") LocalDate startDate,
        @Param("endDate") LocalDate endDate

    );

    List<Map<String, Object>> getMonthlyExpenseRankChart(
        @Param("bId") String bId,
        @Param("startDate") LocalDate startDate,
        @Param("endDate") LocalDate endDate

    );

    List<Map<String, Object>> getSalesHistory(
        @Param("bId") String bId,
        @Param("startDate") LocalDate startDate,
        @Param("endDate") LocalDate endDate
    );

    Long getTotalSales(
        @Param("bId") String bId,
        @Param("startDate") LocalDate startDate,
        @Param("endDate") LocalDate endDate

    );

    List<Map<String, Object>> getMenuSalesRank(
        @Param("bId") String bId,
        @Param("startDate") LocalDate startDate,
        @Param("endDate") LocalDate endDate

    );

    Double getDisposalRate(
        @Param("bId") String bId,
        @Param("startDate") LocalDate startDate,
        @Param("endDate") LocalDate endDate

    );

    Long getTotalDisposalPrice(
        @Param("bId") String bId,
        @Param("startDate") LocalDate startDate,
        @Param("endDate") LocalDate endDate

    );

    List<Map<String, Object>> getTopDisposalMaterials(
        @Param("bId") String bId,
        @Param("startDate") LocalDate startDate,
        @Param("endDate") LocalDate endDate

    );

    List<Map<String, Object>> getDisposalReasonRatio(
        @Param("bId") String bId,
        @Param("startDate") LocalDate startDate,
        @Param("endDate") LocalDate endDate

    );

    List<Map<String, Object>> getDailyDisposalChart(
        @Param("bId") String bId,
        @Param("startDate") LocalDate startDate,
        @Param("endDate") LocalDate endDate

    );
}
