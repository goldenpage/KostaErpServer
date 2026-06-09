package com.oopsw.kostaerpserver.dto.statistics;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MenuSalesRank {
    private int ranking;
    private String menuId;
    private String menuName;
    private int menuPrice;
    private int totalCount;
    private int totalSalePrice;

}
