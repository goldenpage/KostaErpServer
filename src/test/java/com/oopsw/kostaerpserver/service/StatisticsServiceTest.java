package com.oopsw.kostaerpserver.service;


import java.time.LocalDate;
import java.util.List;
import java.util.Map;
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
        List<Map<String, Object>> list =
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
        List<Map<String,Object>> list =
            statisticsService.getMonthlyExpenseRankChart(
            bId,
            LocalDate.of(2026, 5, 1), LocalDate.of(2026, 6, 1));
        assertNotNull(list);

    }

    @Test
    void getSalesHistoryTest() {
        List<Map<String, Object>> list = statisticsService.getSalesHistory(
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
        List<Map<String, Object>> list = statisticsService.getMenuSalesRank(
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
        List<Map<String,Object>> list =
            statisticsService.getTopDisposalMaterials(
            bId,
            LocalDate.of(2026, 5, 1), LocalDate.of(2026, 6, 1));

        assertNotNull(list);
    }


    @Test
    void getDisposalReasonRatioTest() {
        List<Map<String, Object>> list =
            statisticsService.getDisposalReasonRatio(
            bId,
            LocalDate.of(2026, 5, 1), LocalDate.of(2026, 6, 1));

        assertNotNull(list);
    }


    @Test
    void getDailyDisposalChartTest() {
        List<Map<String,Object>> list = statisticsService.getDailyDisposalChart(
            bId,
            LocalDate.of(2026, 5, 1), LocalDate.of(2026, 6, 1));

        assertNotNull(list);
    }

}
