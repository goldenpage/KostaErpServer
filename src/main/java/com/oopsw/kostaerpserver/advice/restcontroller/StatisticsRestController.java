package com.oopsw.kostaerpserver.advice.restcontroller;


import com.oopsw.kostaerpserver.auth.userdetails.AccountDetails;
import com.oopsw.kostaerpserver.dto.statistics.DailyDisposalChart;
import com.oopsw.kostaerpserver.dto.statistics.DisposalRateResponse;
import com.oopsw.kostaerpserver.dto.statistics.DisposalReasonRatio;
import com.oopsw.kostaerpserver.dto.statistics.DisposalTopMaterialsResponse;
import com.oopsw.kostaerpserver.dto.statistics.MenuSalesRank;
import com.oopsw.kostaerpserver.dto.statistics.MonthlyExpense;
import com.oopsw.kostaerpserver.dto.statistics.MonthlyExpenseRankChart;
import com.oopsw.kostaerpserver.dto.statistics.MonthlyFoodMaterialExpenseRank;
import com.oopsw.kostaerpserver.dto.statistics.MonthlyRevenue;
import com.oopsw.kostaerpserver.dto.statistics.SalesHistory;
import com.oopsw.kostaerpserver.dto.statistics.StatisticsRequest;
import com.oopsw.kostaerpserver.service.Interface.StatisticsService;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
            @AuthenticationPrincipal AccountDetails accountDetails
            ) {
        String bId = accountDetails.getAccount().getUsername();
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
        @AuthenticationPrincipal AccountDetails accountDetails) {
        String bId = accountDetails.getAccount().getUsername();

        Long disposalTotalPrice =
            statisticsService.getTotalDisposalPrice(bId,
                statisticsRequest.getStartDate(),
                statisticsRequest.getEndDate());

        return ResponseEntity.ok(disposalTotalPrice);
    }


    @GetMapping("/disposals/top-materials")
    public ResponseEntity<List<DisposalTopMaterialsResponse>> getDisposalTopMaterials(
        @ModelAttribute StatisticsRequest statisticsRequest,
        @AuthenticationPrincipal AccountDetails accountDetails
    ) {
        String bId = accountDetails.getAccount().getUsername();
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
        @AuthenticationPrincipal AccountDetails accountDetails) {
        String bId = accountDetails.getAccount().getUsername();

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
        @AuthenticationPrincipal AccountDetails accountDetails) {

        String bId = accountDetails.getAccount().getUsername();

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
        @AuthenticationPrincipal AccountDetails accountDetails) {

        String bId = accountDetails.getAccount().getUsername();

        Long result = statisticsService.getTotalSales(bId,
            statisticsRequest.getStartDate(),
            statisticsRequest.getEndDate()
        );

        return ResponseEntity.ok(result);
    }

    @GetMapping("/revenue/history")
    public ResponseEntity<List<SalesHistory>> getSalesHistory(
        @ModelAttribute StatisticsRequest statisticsRequest,
        @AuthenticationPrincipal AccountDetails accountDetails) {
        String bId = accountDetails.getAccount().getUsername();

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
        @AuthenticationPrincipal AccountDetails accountDetails) {
        String bId = accountDetails.getAccount().getUsername();

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
        @AuthenticationPrincipal AccountDetails accountDetails) {

        String bId = accountDetails.getAccount().getUsername();

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
        @AuthenticationPrincipal AccountDetails accountDetails) {
        String bId = accountDetails.getAccount().getUsername();

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
        @AuthenticationPrincipal AccountDetails accountDetails) {

        String bId = accountDetails.getAccount().getUsername();

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
        @AuthenticationPrincipal AccountDetails accountDetails
    ) {
        String bId = accountDetails.getAccount().getUsername();

        return ResponseEntity.ok(
            statisticsService.getMonthlyRevenue(
                bId,
                request.getStartDate(),
                request.getEndDate()
            )
        );
    }

    @GetMapping("/expenses/monthly")
    public ResponseEntity<List<MonthlyExpense>> getMonthlyExpense(
        @ModelAttribute StatisticsRequest request,
        @AuthenticationPrincipal AccountDetails accountDetails
    ) {
        String bId = accountDetails.getAccount().getUsername();

        return ResponseEntity.ok(
            statisticsService.getMonthlyExpense(
                bId,
                request.getStartDate(),
                request.getEndDate()
            )
        );
    }
}
