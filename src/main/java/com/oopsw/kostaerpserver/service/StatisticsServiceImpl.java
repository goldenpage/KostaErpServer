package com.oopsw.kostaerpserver.service;

import com.oopsw.kostaerpserver.repository.StatisticsDAO;
import com.oopsw.kostaerpserver.service.Interface.StatisticsService;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StatisticsServiceImpl implements StatisticsService {

    private final StatisticsDAO statisticsDAO;

    @Override
    public List<Map<String, Object>> getMonthlyFoodMaterialExpenseRank(
        String bId, LocalDate startDate, LocalDate endDate) {
        validateSearchCondition(bId, startDate, endDate);
        return statisticsDAO.getMonthlyFoodMaterialExpenseRank(bId, startDate, endDate);

    }

    @Override
    public Long getTotalExpense(String bId, LocalDate startDate,
        LocalDate endDate)  {
        validateSearchCondition(bId, startDate, endDate);
        return statisticsDAO.getTotalExpense(bId, startDate,endDate);

    }

    @Override
    public List<Map<String, Object>> getMonthlyExpenseRankChart(String bId,
        LocalDate startDate, LocalDate endDate)  {
        validateSearchCondition(bId, startDate, endDate);

        return statisticsDAO.getMonthlyExpenseRankChart(bId, startDate,
            endDate);

    }

    @Override
    public List<Map<String, Object>> getSalesHistory(String bId,
        LocalDate startDate, LocalDate endDate)  {
        validateSearchCondition(bId, startDate, endDate);
        return statisticsDAO.getSalesHistory(bId, startDate, endDate);

    }

    @Override
    public Long getTotalSales(String bId, LocalDate startDate,
        LocalDate endDate)  {
        validateSearchCondition(bId, startDate, endDate);
        return statisticsDAO.getTotalSales(bId, startDate, endDate);

    }

    @Override
    public List<Map<String, Object>> getMenuSalesRank(String bId,
        LocalDate startDate, LocalDate endDate) {
        validateSearchCondition(bId, startDate, endDate);
        return statisticsDAO.getMenuSalesRank(bId, startDate, endDate);

    }

    @Override
    public Double getDisposalRate(String bId, LocalDate startDate,
        LocalDate endDate)  {
        validateSearchCondition(bId, startDate, endDate);
        return statisticsDAO.getDisposalRate(bId, startDate, endDate);

    }

    @Override
    public Long getTotalDisposalPrice(String bId, LocalDate startDate,
        LocalDate endDate)  {
        validateSearchCondition(bId, startDate, endDate);
        return statisticsDAO.getTotalDisposalPrice(bId, startDate, endDate);

    }

    @Override
    public List<Map<String, Object>> getTopDisposalMaterials(String bId,
        LocalDate startDate, LocalDate endDate)  {
        validateSearchCondition(bId, startDate, endDate);
        return statisticsDAO.getTopDisposalMaterials(bId, startDate, endDate);

    }

    @Override
    public List<Map<String, Object>> getDisposalReasonRatio(String bId,
        LocalDate startDate, LocalDate endDate)  {
        validateSearchCondition(bId, startDate, endDate);
        return statisticsDAO.getDisposalReasonRatio(bId, startDate, endDate);

    }

    @Override
    public List<Map<String, Object>> getDailyDisposalChart(String bId,
        LocalDate startDate, LocalDate endDate)  {
        validateSearchCondition(bId, startDate, endDate);
        return statisticsDAO.getDailyDisposalChart(bId, startDate, endDate);

    }

    private void validateSearchCondition(String bId, LocalDate startDate, LocalDate endDate) {
        if (bId == null || bId.isBlank()) {
            throw new IllegalArgumentException("사업자 ID는 필수입니다.");
        }
        if (startDate == null || endDate == null) {
            throw new IllegalArgumentException("조회 기간은 필수입니다.");
        }
        if (startDate.isAfter(endDate)) {
            throw new IllegalArgumentException("시작일은 종료일보다 늦을 수 없습니다.");
        }

    }
}
