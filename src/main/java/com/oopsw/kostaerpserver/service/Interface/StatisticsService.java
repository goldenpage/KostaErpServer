package com.oopsw.kostaerpserver.service.Interface;

import com.oopsw.kostaerpserver.dto.DailyDisposalChart;
import com.oopsw.kostaerpserver.dto.DisposalReasonRatio;
import com.oopsw.kostaerpserver.dto.DisposalTopMaterialsResponse;
import com.oopsw.kostaerpserver.dto.MenuSalesRank;
import com.oopsw.kostaerpserver.dto.MonthlyExpense;
import com.oopsw.kostaerpserver.dto.MonthlyExpenseRankChart;
import com.oopsw.kostaerpserver.dto.MonthlyFoodMaterialExpenseRank;
import com.oopsw.kostaerpserver.dto.MonthlyRevenue;
import com.oopsw.kostaerpserver.dto.SalesHistory;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

@Service
public interface StatisticsService {

    List<MonthlyFoodMaterialExpenseRank> getMonthlyFoodMaterialExpenseRank(
        @Param("bId") String bId,
        @Param("startDate") LocalDate startDate,
        @Param("endDate") LocalDate endDate
    );

    Long getTotalExpense(
        @Param("bId") String bId,
        @Param("startDate") LocalDate startDate,
        @Param("endDate") LocalDate endDate
    ) ;

    List<MonthlyExpenseRankChart> getMonthlyExpenseRankChart(
        @Param("bId") String bId,
        @Param("startDate") LocalDate startDate,
        @Param("endDate") LocalDate endDate
    ) ;

    List<SalesHistory> getSalesHistory(
        @Param("bId") String bId,
        @Param("startDate") LocalDate startDate,
        @Param("endDate") LocalDate endDate
    ) ;

    Long getTotalSales(
        @Param("bId") String bId,
        @Param("startDate") LocalDate startDate,
        @Param("endDate") LocalDate endDate

    ) ;

    List<MenuSalesRank> getMenuSalesRank(
        @Param("bId") String bId,
        @Param("startDate") LocalDate startDate,
        @Param("endDate") LocalDate endDate
    ) ;

    Double getDisposalRate(
        @Param("bId") String bId,
        @Param("startDate") LocalDate startDate,
        @Param("endDate") LocalDate endDate

    ) ;

    Long getTotalDisposalPrice(
        @Param("bId") String bId,
        @Param("startDate") LocalDate startDate,
        @Param("endDate") LocalDate endDate

    ) ;

    List<DisposalTopMaterialsResponse> getTopDisposalMaterials(
        @Param("bId") String bId,
        @Param("startDate") LocalDate startDate,
        @Param("endDate") LocalDate endDate

    ) ;

    List<DisposalReasonRatio> getDisposalReasonRatio(
        @Param("bId") String bId,
        @Param("startDate") LocalDate startDate,
        @Param("endDate") LocalDate endDate

    ) ;

    List<DailyDisposalChart> getDailyDisposalChart(
        @Param("bId") String bId,
        @Param("startDate") LocalDate startDate,
        @Param("endDate") LocalDate endDate

    ) ;

    List<MonthlyRevenue> getMonthlyRevenue(
        String bId,
        @Param("startDate") LocalDate startDate,
        @Param("endDate") LocalDate endDate
    );

    List<MonthlyExpense> getMonthlyExpense(
        String bId,
        LocalDate startDate,
        LocalDate endDate
    );
}
