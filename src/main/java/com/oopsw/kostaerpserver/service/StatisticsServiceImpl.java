package com.oopsw.kostaerpserver.service;

import com.oopsw.kostaerpserver.repository.StatisticsDAO;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StatisticsServiceImpl implements StatisticsService{

    @Autowired
    StatisticsDAO statisticsDAO;

    @Override
    public List<Map<String, Object>> getMonthlyFoodMaterialExpenseRank(
        String bId, LocalDate startDate, LocalDate endDate) {
        if (bId == null || bId.isBlank()) {
            throw new IllegalArgumentException("bId는 필수값입니다.");
        }
        if (startDate == null || endDate == null) {
            throw new IllegalArgumentException("조회 시작일과 종료일은 필수값입니다.");
        }
        if (startDate.isAfter(endDate)) {
            throw new IllegalArgumentException("시작일은 종료일보다 늦을 수 없습니다.");
        }
        return statisticsDAO.getMonthlyFoodMaterialExpenseRank(bId, startDate, endDate);

    }

    @Override
    public Long getTotalExpense(String bId, LocalDate startDate,
        LocalDate endDate) {
        statisticsDAO.getTotalExpense(bId, startDate,endDate);
        return 0L;
    }

    @Override
    public List<Map<String, Object>> getMonthlyExpenseRankChart(String bId,
        LocalDate startDate, LocalDate endDate) {
        statisticsDAO.getMonthlyExpenseRankChart(bId, startDate, endDate);
        return List.of();
    }

    @Override
    public List<Map<String, Object>> getSalesHistory(String bId,
        LocalDate startDate, LocalDate endDate) {
        statisticsDAO.getSalesHistory(bId, startDate, endDate);
        return List.of();
    }

    @Override
    public Long getTotalSales(String bId, LocalDate startDate,
        LocalDate endDate) {
        statisticsDAO.getTotalSales(bId, startDate, endDate);
        return 0L;
    }

    @Override
    public List<Map<String, Object>> getMenuSalesRank(String bId,
        LocalDate startDate, LocalDate endDate) {
        statisticsDAO.getMenuSalesRank(bId, startDate, endDate);
        return List.of();
    }

    @Override
    public Double getDisposalRate(String bId, LocalDate startDate,
        LocalDate endDate) {
        statisticsDAO.getDisposalRate(bId, startDate, endDate);
        return 0.0;
    }

    @Override
    public Long getTotalDisposalPrice(String bId, LocalDate startDate,
        LocalDate endDate) {
        statisticsDAO.getTotalDisposalPrice(bId, startDate, endDate);
        return 0L;
    }

    @Override
    public List<Map<String, Object>> getTopDisposalMaterials(String bId,
        LocalDate startDate, LocalDate endDate) {
        statisticsDAO.getTopDisposalMaterials(bId, startDate, endDate);
        return List.of();
    }

    @Override
    public List<Map<String, Object>> getDisposalReasonRatio(String bId,
        LocalDate startDate, LocalDate endDate) {
        statisticsDAO.getDisposalReasonRatio(bId, startDate, endDate);
        return List.of();
    }

    @Override
    public List<Map<String, Object>> getDailyDisposalChart(String bId,
        LocalDate startDate, LocalDate endDate) {
        statisticsDAO.getDailyDisposalChart(bId, startDate, endDate);
        return List.of();
    }
}
