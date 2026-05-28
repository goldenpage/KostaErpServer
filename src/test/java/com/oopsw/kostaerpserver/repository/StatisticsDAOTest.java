package com.oopsw.kostaerpserver.repository;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.YearMonth;
import org.springframework.test.context.ActiveProfiles;

@Slf4j
@ActiveProfiles("test")
@SpringBootTest
public class StatisticsDAOTest {

    @Autowired
    StatisticsDAO statisticsDAO;

    private String bId() {
        return "0000000000";
    }

    private LocalDate startDate() {
        return YearMonth.of(2026, 4).atDay(1);
    }

    private LocalDate endDate() {
        return YearMonth.of(2026, 4).plusMonths(1).atDay(1);
    }

    @Test
    void getMonthlyFoodMaterialExpenseRankTest() {
        log.info("월별 지출 식자재 순위 = {}",
            statisticsDAO.getMonthlyFoodMaterialExpenseRank(bId(), startDate(), endDate()));
    }

    @Test
    void getTotalExpenseTest() {
        log.info("총 지출액 = {}",
            statisticsDAO.getTotalExpense(bId(), startDate(), endDate()));
    }

    @Test
    void getMonthlyExpenseRankChartTest() {
        log.info("월별 지출 순위 그래프 = {}",
            statisticsDAO.getMonthlyExpenseRankChart(bId(), startDate(), endDate()));
    }

    @Test
    void getSalesHistoryTest() {
        log.info("판매내역 조회 = {}",
            statisticsDAO.getSalesHistory(bId(), startDate(), endDate()));
    }

    @Test
    void getTotalSalesTest() {
        log.info("총 매출액 조회 = {}",
            statisticsDAO.getTotalSales(bId(), startDate(), endDate()));
    }

    @Test
    void getMenuSalesRankTest() {
        log.info("메뉴 판매순위 조회 = {}",
            statisticsDAO.getMenuSalesRank(bId(), startDate(), endDate()));
    }

    @Test
    void getDisposalRateTest() {
        log.info("폐기율 = {}",
            statisticsDAO.getDisposalRate(bId(), startDate(), endDate()));
    }

    @Test
    void getTotalDisposalPriceTest() {
        log.info("폐기금액 = {}",
            statisticsDAO.getTotalDisposalPrice(bId(), startDate(), endDate()));
    }

    @Test
    void getTopDisposalMaterialsTest() {
        log.info("폐기품목 Top3 = {}",
            statisticsDAO.getTopDisposalMaterials(bId(), startDate(), endDate()));
    }

    @Test
    void getDisposalReasonRatioTest() {
        log.info("폐기사유비율 = {}",
            statisticsDAO.getDisposalReasonRatio(bId(), startDate(), endDate()));
    }

    @Test
    void getDailyDisposalChartTest() {
        log.info("날짜별 폐기량 그래프 = {}",
            statisticsDAO.getDailyDisposalChart(bId(), startDate(), endDate()));
    }
}