package com.oopsw.kostaerpserver.vo;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;


@Data
@AllArgsConstructor
@Builder
public class Statistics {

    // 공통 검색 조건
    private String bId;
    private LocalDate startDate;
    private LocalDate endDate;

    // 순위 공통
    private Integer ranking;

    // 식자재 / 지출 통계
    private String foodMaterialId;
    private String foodMaterialName;
    private Integer foodMaterialPrice;
    private Integer foodMaterialCount;
    private Long totalExpense;
    private LocalDate incomeDate;

    // 매출 / 판매 통계
    private String saleId;
    private Integer saleMenuCount;
    private String menuId;
    private String menuName;
    private Integer menuPrice;
    private String revenueId;
    private LocalDate revenueDate;
    private Long saleTotalPrice;
    private Long totalSales;
    private Long totalSaleCount;
    private Long totalSalePrice;

    // 폐기 통계
    private Double disposalRate;
    private Long totalDisposalPrice;
    private Long disposalCount;
    private LocalDate disposalDay;
    private String foodMaterialType;

    // 폐기사유
    private String reasonId;
    private String reason;
    private Long reasonCount;
    private Double reasonRatio;


}