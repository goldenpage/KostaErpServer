package com.oopsw.kostaerpserver.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MonthlyFoodMaterialExpenseRank {
    private int ranking;
    private String foodMaterialId;
    private String foodMaterialName;
    private String incomeDate;
    private int foodMaterialPrice;
    private int foodMaterialCount;
    private int totalExpense;


}
