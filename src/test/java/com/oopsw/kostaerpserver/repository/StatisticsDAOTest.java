package com.oopsw.kostaerpserver.repository;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.YearMonth;

@Slf4j
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
    void getMonthlyFoodMaterialExpenseRank() {
        log.info("월별 지출 식자재 순위 = {}",
            statisticsDAO.getMonthlyFoodMaterialExpenseRank(bId(), startDate(), endDate()));
    }

    @Test
    void getTotalExpense() {
        log.info("총 지출액 = {}",
            statisticsDAO.getTotalExpense(bId(), startDate(), endDate()));
    }

    @Test
    void getMonthlyExpenseRankChart() {
        log.info("월별 지출 순위 그래프 = {}",
            statisticsDAO.getMonthlyExpenseRankChart(bId(), startDate(), endDate()));
    }

    @Test
    void getSalesHistory() {
        log.info("판매내역 조회 = {}",
            statisticsDAO.getSalesHistory(bId(), startDate(), endDate()));
    }

    @Test
    void getTotalSales() {
        log.info("총 매출액 조회 = {}",
            statisticsDAO.getTotalSales(bId(), startDate(), endDate()));
    }

    @Test
    void getMenuSalesRank() {
        log.info("메뉴 판매순위 조회 = {}",
            statisticsDAO.getMenuSalesRank(bId(), startDate(), endDate()));
    }

    @Test
    void getDisposalRate() {
        log.info("폐기율 = {}",
            statisticsDAO.getDisposalRate(bId(), startDate(), endDate()));
    }

    @Test
    void getTotalDisposalPrice() {
        log.info("폐기금액 = {}",
            statisticsDAO.getTotalDisposalPrice(bId(), startDate(), endDate()));
    }

    @Test
    void getTopDisposalMaterials() {
        log.info("폐기품목 Top3 = {}",
            statisticsDAO.getTopDisposalMaterials(bId(), startDate(), endDate()));
    }

    @Test
    void getDisposalReasonRatio() {
        log.info("폐기사유비율 = {}",
            statisticsDAO.getDisposalReasonRatio(bId(), startDate(), endDate()));
    }

    @Test
    void getDailyDisposalChart() {
        log.info("날짜별 폐기량 그래프 = {}",
            statisticsDAO.getDailyDisposalChart(bId(), startDate(), endDate()));
    }
}