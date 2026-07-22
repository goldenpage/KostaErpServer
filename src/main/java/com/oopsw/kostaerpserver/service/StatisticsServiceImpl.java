package com.oopsw.kostaerpserver.service;

import com.oopsw.kostaerpserver.dto.statistics.DailyDisposalChart;
import com.oopsw.kostaerpserver.dto.statistics.DisposalReasonRatio;
import com.oopsw.kostaerpserver.dto.statistics.DisposalTopMaterialsResponse;
import com.oopsw.kostaerpserver.dto.statistics.MenuSalesRank;
import com.oopsw.kostaerpserver.dto.statistics.MonthlyExpense;
import com.oopsw.kostaerpserver.dto.statistics.MonthlyExpenseRankChart;
import com.oopsw.kostaerpserver.dto.statistics.MonthlyFoodMaterialExpenseRank;
import com.oopsw.kostaerpserver.dto.statistics.MonthlyRevenue;
import com.oopsw.kostaerpserver.dto.statistics.SalesHistory;
import com.oopsw.kostaerpserver.repository.dao.StatisticsDAO;
import com.oopsw.kostaerpserver.service.Interface.StatisticsService;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StatisticsServiceImpl implements StatisticsService {

    private final StatisticsDAO statisticsDAO;

    @Override
    public List<MonthlyFoodMaterialExpenseRank> getMonthlyFoodMaterialExpenseRank(
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
    public List<MonthlyExpenseRankChart> getMonthlyExpenseRankChart(String bId,
        LocalDate startDate, LocalDate endDate)  {
        validateSearchCondition(bId, startDate, endDate);

        return statisticsDAO.getMonthlyExpenseRankChart(bId, startDate,
            endDate);
    }

    @Override
    public List<SalesHistory> getSalesHistory(String bId,
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
    public List<MenuSalesRank> getMenuSalesRank(String bId,
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
    public List<DisposalTopMaterialsResponse> getTopDisposalMaterials(
        String bId,
        LocalDate startDate,
        LocalDate endDate
    ) {
        validateSearchCondition(bId, startDate, endDate);
        List<Map<String, Object>> list = statisticsDAO.getTopDisposalMaterials(
            bId,
            startDate,
            endDate
        );

        return list.stream()
            .map(item -> DisposalTopMaterialsResponse.builder()
                .foodMaterialId(String.valueOf(item.get("foodMaterialId")))
                .foodMaterialName(String.valueOf(item.get("foodMaterialName")))
                .disposalCount(((Number) item.get("disposalCount")).intValue())
                .totalDisposalPrice(((Number) item.get("totalDisposalPrice")).intValue())
                .build()
            )
            .toList();
    }

    @Override
    public List<DisposalReasonRatio> getDisposalReasonRatio(String bId,
        LocalDate startDate, LocalDate endDate)  {
        validateSearchCondition(bId, startDate, endDate);
        return statisticsDAO.getDisposalReasonRatio(bId, startDate, endDate);

    }

    @Override
    public List<DailyDisposalChart> getDailyDisposalChart(String bId,
        LocalDate startDate, LocalDate endDate)  {
        validateSearchCondition(bId, startDate, endDate);

        return statisticsDAO.getDailyDisposalChart( bId,
            startDate,
            endDate);
    }

    @Override
    public List<MonthlyRevenue> getMonthlyRevenue(
        String bId,
        LocalDate startDate,
        LocalDate endDate
    ) {
        validateSearchCondition(bId, startDate, endDate);
        return statisticsDAO.getMonthlyRevenue(bId, startDate, endDate);
    }


    @Override
    public List<MonthlyExpense> getMonthlyExpense(String bId,
        LocalDate startDate, LocalDate endDate) {
        validateSearchCondition(bId, startDate, endDate);
        return statisticsDAO.getMonthlyExpense(bId, startDate, endDate);
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
