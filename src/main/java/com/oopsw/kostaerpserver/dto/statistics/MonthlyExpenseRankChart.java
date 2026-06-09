package com.oopsw.kostaerpserver.dto.statistics;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MonthlyExpenseRankChart {

    private int ranking;
    private String foodMaterialName;
    private int totalExpense;

}
