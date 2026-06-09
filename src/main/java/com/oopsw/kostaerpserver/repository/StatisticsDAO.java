package com.oopsw.kostaerpserver.repository;

import com.oopsw.kostaerpserver.dto.statistics.DailyDisposalChart;
import com.oopsw.kostaerpserver.dto.statistics.DisposalReasonRatio;
import com.oopsw.kostaerpserver.dto.statistics.MenuSalesRank;
import com.oopsw.kostaerpserver.dto.statistics.MonthlyExpense;
import com.oopsw.kostaerpserver.dto.statistics.MonthlyExpenseRankChart;
import com.oopsw.kostaerpserver.dto.statistics.MonthlyFoodMaterialExpenseRank;
import com.oopsw.kostaerpserver.dto.statistics.MonthlyRevenue;
import com.oopsw.kostaerpserver.dto.statistics.SalesHistory;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface StatisticsDAO {

    List<MonthlyFoodMaterialExpenseRank> getMonthlyFoodMaterialExpenseRank(
        @Param("bId") String bId,
        @Param("startDate") LocalDate startDate,
        @Param("endDate") LocalDate endDate
    );

    Long getTotalExpense(
        @Param("bId") String bId,
        @Param("startDate") LocalDate startDate,
        @Param("endDate") LocalDate endDate
    );

    List<MonthlyExpenseRankChart> getMonthlyExpenseRankChart(
        @Param("bId") String bId,
        @Param("startDate") LocalDate startDate,
        @Param("endDate") LocalDate endDate
    );

    List<SalesHistory> getSalesHistory(
        @Param("bId") String bId,
        @Param("startDate") LocalDate startDate,
        @Param("endDate") LocalDate endDate
    );

    Long getTotalSales(
        @Param("bId") String bId,
        @Param("startDate") LocalDate startDate,
        @Param("endDate") LocalDate endDate

    );

    List<MenuSalesRank> getMenuSalesRank(
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

    List<DisposalReasonRatio> getDisposalReasonRatio(
        @Param("bId") String bId,
        @Param("startDate") LocalDate startDate,
        @Param("endDate") LocalDate endDate

    );

    List<DailyDisposalChart> getDailyDisposalChart(
        @Param("bId") String bId,
        @Param("startDate") LocalDate startDate,
        @Param("endDate") LocalDate endDate
    );

    List<MonthlyRevenue> getMonthlyRevenue(
        @Param("bId") String bId,
        @Param("startDate") LocalDate startDate,
        @Param("endDate") LocalDate endDate
    );

    List<MonthlyExpense> getMonthlyExpense(
        String bId,
        LocalDate startDate,
        LocalDate endDate
    );
}
