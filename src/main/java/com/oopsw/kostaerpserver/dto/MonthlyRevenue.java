package com.oopsw.kostaerpserver.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MonthlyRevenue {
    private String revenueMonth;
    private Long totalRevenuePrice;
}
