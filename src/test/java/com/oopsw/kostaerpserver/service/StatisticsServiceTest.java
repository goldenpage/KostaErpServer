package com.oopsw.kostaerpserver.service;


import com.oopsw.kostaerpserver.dto.statistics.DailyDisposalChart;
import com.oopsw.kostaerpserver.dto.statistics.DisposalReasonRatio;
import com.oopsw.kostaerpserver.dto.statistics.DisposalTopMaterialsResponse;
import com.oopsw.kostaerpserver.dto.statistics.MenuSalesRank;
import com.oopsw.kostaerpserver.dto.statistics.MonthlyExpenseRankChart;
import com.oopsw.kostaerpserver.dto.statistics.MonthlyFoodMaterialExpenseRank;
import com.oopsw.kostaerpserver.dto.statistics.SalesHistory;
import com.oopsw.kostaerpserver.service.Interface.StatisticsService;
import java.time.LocalDate;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@Slf4j
@ActiveProfiles("test")
@SpringBootTest
public class StatisticsServiceTest {

    @Autowired
    StatisticsService statisticsService;

    String bId = "0000000000";

    @Test
    void getMonthlyFoodMaterialExpenseRankTest() {
        List<MonthlyFoodMaterialExpenseRank> list =
            statisticsService.getMonthlyFoodMaterialExpenseRank(bId,
                LocalDate.of(2026, 5, 1), LocalDate.of(2026, 6, 1));

        assertNotNull(list);
    }

    @Test
    void getTotalExpenseTest() {
        Long result = statisticsService.getTotalExpense(bId,
            LocalDate.of(2026, 5, 1), LocalDate.of(2026, 6, 1));

        assertNotNull(result);
    }

    @Test
    void getMonthlyExpenseRankChartTest() {
        List<MonthlyExpenseRankChart> list =
            statisticsService.getMonthlyExpenseRankChart(
            bId,
            LocalDate.of(2026, 5, 1), LocalDate.of(2026, 6, 1));
        assertNotNull(list);

    }

    @Test
    void getSalesHistoryTest() {
        List<SalesHistory> list = statisticsService.getSalesHistory(
            bId,
            LocalDate.of(2026, 5, 1), LocalDate.of(2026, 6, 1));
        assertNotNull(list);
    }





    @Test
    void getTotalSalesTest() {
        Long result = statisticsService.getTotalSales(bId,
            LocalDate.of(2026, 5, 1), LocalDate.of(2026, 6, 1));

        assertNotNull(result);
    }



    @Test
    void getMenuSalesRankTest() {
        List<MenuSalesRank> list = statisticsService.getMenuSalesRank(
            bId,
            LocalDate.of(2026, 5, 1), LocalDate.of(2026, 6, 1));

        assertNotNull(list);
    }


    @Test
    void getDisposalRateTest() {
        Double result = statisticsService.getDisposalRate(bId,
            LocalDate.of(2026, 5, 1), LocalDate.of(2026, 6, 1));

        assertNotNull(result);
    }


    @Test
    void getTotalDisposalPriceTest() {
        Long result = statisticsService.getTotalDisposalPrice(bId,
            LocalDate.of(2026, 5, 1), LocalDate.of(2026, 6, 1));

            assertNotNull(result);
    }

    @Test
    void getTopDisposalMaterialsTest() {
        List<DisposalTopMaterialsResponse> list =
            statisticsService.getTopDisposalMaterials(
            bId,
            LocalDate.of(2026, 5, 1), LocalDate.of(2026, 6, 1));

        assertNotNull(list);
    }


    @Test
    void getDisposalReasonRatioTest() {
        List<DisposalReasonRatio> list =
            statisticsService.getDisposalReasonRatio(
            bId,
            LocalDate.of(2026, 5, 1), LocalDate.of(2026, 6, 1));

        assertNotNull(list);
    }


    @Test
    void getDailyDisposalChartTest() {
        List<DailyDisposalChart> list = statisticsService.getDailyDisposalChart(
            bId,
            LocalDate.of(2026, 5, 1), LocalDate.of(2026, 6, 1));

        assertNotNull(list);
    }

}
