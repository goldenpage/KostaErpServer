package com.oopsw.kostaerpserver.dto.statistics;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SalesHistory {
    private String saleId;
    private int saleMenuCount;
    private String menuId;
    private String menuName;
    private int menuPrice;
    private String revenueId;
    private String revenueDate;
    private int saleTotalPrice;

}
