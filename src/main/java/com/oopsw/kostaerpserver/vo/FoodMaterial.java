package com.oopsw.kostaerpserver.vo;

import lombok.*;

import java.util.Date;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class FoodMaterial {
    private String foodMaterialId;
    private String foodMaterialName;
    private String foodCategory;
    private int foodMaterialCount;
    private int foodMaterialCountAll;
    private int foodMaterialPrice;
    private String vender;
    private String foodMaterialType;
    private Date incomeDate;
    private Date expirationDate;
    private String bId;
    private int ranking;
    private int totalExpense;

}
