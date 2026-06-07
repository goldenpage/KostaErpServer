package com.oopsw.kostaerpserver.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MonthlyExpense {

    private String expenseMonth;
    private Long totalExpense;
}