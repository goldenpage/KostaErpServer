package com.oopsw.kostaerpserver.restcontroller;


import com.oopsw.kostaerpserver.dto.DailyDisposalChart;
import com.oopsw.kostaerpserver.dto.DisposalRateResponse;
import com.oopsw.kostaerpserver.dto.DisposalReasonRatio;
import com.oopsw.kostaerpserver.dto.DisposalTopMaterialsResponse;
import com.oopsw.kostaerpserver.dto.MenuSalesRank;
import com.oopsw.kostaerpserver.dto.MonthlyExpenseRankChart;
import com.oopsw.kostaerpserver.dto.MonthlyFoodMaterialExpenseRank;
import com.oopsw.kostaerpserver.dto.MonthlyRevenue;
import com.oopsw.kostaerpserver.dto.SalesHistory;
import com.oopsw.kostaerpserver.dto.StatisticsRequest;
import com.oopsw.kostaerpserver.service.Interface.StatisticsService;
import com.oopsw.kostaerpserver.vo.User;
import jakarta.servlet.http.HttpSession;

import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/statistics")
@RequiredArgsConstructor
public class StatisticsRestController {

    private final StatisticsService statisticsService;


    @GetMapping("/disposals/rate")
    public ResponseEntity<DisposalRateResponse> getDisposalRate(
        @ModelAttribute StatisticsRequest statisticsRequest,
        HttpSession session
    ) {
        User user = (User) session.getAttribute("info");

        if (user == null) {
            return ResponseEntity.status(401).build();
        }
        String bId = user.getBId();
        Double response = statisticsService.getDisposalRate(
            bId,
            statisticsRequest.getStartDate(),
            statisticsRequest.getEndDate()
        );
        return ResponseEntity.ok(new DisposalRateResponse(response));
    }

    @GetMapping("/disposals/total-price")
    public ResponseEntity<Long> getTotalDisposalPrice(
        @ModelAttribute StatisticsRequest statisticsRequest,
        HttpSession session) {
        User user = (User) session.getAttribute("info");

        if (user == null) {
            return ResponseEntity.status(401).build();
        }
        String bId = user.getBId();

        Long disposalTotalPrice =
            statisticsService.getTotalDisposalPrice(bId,
                statisticsRequest.getStartDate(),
                statisticsRequest.getEndDate());

        return ResponseEntity.ok(disposalTotalPrice);
    }


    @GetMapping("/disposals/top-materials")
    public ResponseEntity<List<DisposalTopMaterialsResponse>> getDisposalTopMaterials(
        @ModelAttribute StatisticsRequest statisticsRequest,
        HttpSession session
    ) {
        User user = (User) session.getAttribute("info");
        if (user == null) {
            return ResponseEntity.status(401).build();
        }
        String bId = user.getBId();
        List<DisposalTopMaterialsResponse> response =
            statisticsService.getTopDisposalMaterials(
                bId,
                statisticsRequest.getStartDate(),
                statisticsRequest.getEndDate()
            );
        return ResponseEntity.ok(response);
    }


    @GetMapping("/disposals/reason-ratio")
    public ResponseEntity<List<DisposalReasonRatio>> getDisposalReasonRatio(
        @ModelAttribute StatisticsRequest statisticsRequest,
        HttpSession session) {
        User user = (User) session.getAttribute("info");
        if (user == null) {
            return ResponseEntity.status(401).build();
        }

        String bId = user.getBId();

        List<DisposalReasonRatio> list = statisticsService.getDisposalReasonRatio(
            bId,
            statisticsRequest.getStartDate(),
            statisticsRequest.getEndDate()
        );

        return ResponseEntity.ok(list);
    }

    @GetMapping("/disposals/daily-chart")
    public ResponseEntity<List<DailyDisposalChart>> getDailyDisposalChart(
        @ModelAttribute StatisticsRequest statisticsRequest,
        HttpSession session) {

        User user = (User) session.getAttribute("info");
        if (user == null) {
            return ResponseEntity.status(401).build();
        }

        String bId = user.getBId();

        List<DailyDisposalChart> list =
            statisticsService.getDailyDisposalChart(
                bId,
                statisticsRequest.getStartDate(),
                statisticsRequest.getEndDate()
            );

        return ResponseEntity.ok(list);
    }

    @GetMapping("/revenue/total")
    public ResponseEntity<Long> getTotalSales(
        @ModelAttribute StatisticsRequest statisticsRequest,
        HttpSession session) {
        User user = (User) session.getAttribute("info");
        if (user == null) {
            return ResponseEntity.status(401).build();
        }

        String bId = user.getBId();

        Long result = statisticsService.getTotalSales(bId,
            statisticsRequest.getStartDate(),
            statisticsRequest.getEndDate()
        );

        return ResponseEntity.ok(result);
    }

    @GetMapping("/revenue/history")
    public ResponseEntity<List<SalesHistory>> getSalesHistory(
        @ModelAttribute StatisticsRequest statisticsRequest,
        HttpSession session) {
        User user = (User) session.getAttribute("info");
        if (user == null) {
            return ResponseEntity.status(401).build();
        }

        String bId = user.getBId();

        List<SalesHistory> list = statisticsService.getSalesHistory(
            bId,
            statisticsRequest.getStartDate(),
            statisticsRequest.getEndDate()
        );

        return ResponseEntity.ok(list);
    }

    @GetMapping("/revenue/menu-rank")
    public ResponseEntity<List<MenuSalesRank>> getMenuSalesRank(
        @ModelAttribute StatisticsRequest statisticsRequest,
        HttpSession session) {
        User user = (User) session.getAttribute("info");
        if (user == null) {
            return ResponseEntity.status(401).build();
        }

        String bId = user.getBId();

        List<MenuSalesRank> list = statisticsService.getMenuSalesRank(
            bId,
            statisticsRequest.getStartDate(),
            statisticsRequest.getEndDate()
        );

        return ResponseEntity.ok(list);
    }

    @GetMapping("/expenses/material-rank")
    public ResponseEntity<List<MonthlyFoodMaterialExpenseRank>> getMonthlyFoodMaterialExpenseRank(
        @ModelAttribute StatisticsRequest statisticsRequest,
        HttpSession session) {
        User user = (User) session.getAttribute("info");
        if (user == null) {
            return ResponseEntity.status(401).build();
        }

        String bId = user.getBId();

        List<MonthlyFoodMaterialExpenseRank> list = statisticsService.getMonthlyFoodMaterialExpenseRank(
            bId,
            statisticsRequest.getStartDate(),
            statisticsRequest.getEndDate()
        );
        return ResponseEntity.ok(list);
    }

    @GetMapping("/expenses/total")
    public ResponseEntity<Long> getTotalExpense(
        @ModelAttribute StatisticsRequest statisticsRequest,
        HttpSession session) {
        User user = (User) session.getAttribute("info");
        if (user == null) {
            return ResponseEntity.status(401).build();
        }

        String bId = user.getBId();

        Long result = statisticsService.getTotalExpense(
            bId,
            statisticsRequest.getStartDate(),
            statisticsRequest.getEndDate()
        );

        return ResponseEntity.ok(result);

    }

    @GetMapping("/expenses/material-rank/chart")
    public ResponseEntity<List<MonthlyExpenseRankChart>> getMonthlyExpenseRankChart(
        @ModelAttribute StatisticsRequest statisticsRequest,
        HttpSession session) {
        User user = (User) session.getAttribute("info");
        if (user == null) {
            return ResponseEntity.status(401).build();
        }

        String bId = user.getBId();
        List<MonthlyExpenseRankChart> list = statisticsService.getMonthlyExpenseRankChart(
            bId,
            statisticsRequest.getStartDate(),
            statisticsRequest.getEndDate()
        );
        return ResponseEntity.ok(list);
    }

    @GetMapping("/revenue/monthly")
    public ResponseEntity<List<MonthlyRevenue>> getMonthlyRevenue(
        @ModelAttribute StatisticsRequest request,
        HttpSession session
    ) {
        User user = (User) session.getAttribute("info");

        if (user == null) {
            return ResponseEntity.status(401).build();
        }

        return ResponseEntity.ok(
            statisticsService.getMonthlyRevenue(
                user.getBId(),
                request.getStartDate(),
                request.getEndDate()
            )
        );
    }
}
