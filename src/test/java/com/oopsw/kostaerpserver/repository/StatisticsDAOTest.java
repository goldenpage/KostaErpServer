package com.oopsw.kostaerpserver.repository;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@Slf4j
@SpringBootTest
public class StatisticsDAOTest {

    @Autowired
    StatisticsDAO statisticsDAO;

    @Test
    void getMonthlyFoodMaterialExpenseRank() {
      log.info(statisticsDAO.getMonthlyFoodMaterialExpenseRank());
    }

    @Test
    void getTotalExpense() {
        log.info(statisticsDAO.getTotalExpense());
    }

    @Test
    void getMonthlyExpenseRankChart() {
        log.info(statisticsDAO.getMonthlyExpenseRankChart());
    }

    @Test
    void getSalesHistory() {
        log.info(statisticsDAO.getSalesHistory());
    }

    @Test
    void getTotalSales() {
        log.info(statisticsDAO.getTotalSales());
    }

    @Test
    void getMenuSalesRank() {
        log.info(statisticsDAO.getMenuSalesRank());
    }

    @Test
    void getDisposalRate() {
        log.info(statisticsDAO.getDisposalRate());
    }

    @Test
    void getTotalDisposalPrice() {
        log.info(statisticsDAO.getTotalDisposalPrice());
    }

    @Test
    void getTopDisposalMaterials() {
        log.info(statisticsDAO.getTopDisposalMaterials());
    }

    @Test
    void getDisposalReasonRatio() {
        log.info(statisticsDAO.getDisposalReasonRatio());
    }

    @Test
    void getDailyDisposalChart() {
        log.info(statisticsDAO.getDailyDisposalChart());
    }
}
