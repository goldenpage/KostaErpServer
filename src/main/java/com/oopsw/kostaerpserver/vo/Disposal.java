package com.oopsw.kostaerpserver.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Disposal {
    private String disposalId;
    private int disposalCountAll;
    private int disposalPrice;
    private LocalDate disposalDate;
    private String foodMaterialId;
    private String reasonId;

    private String foodMaterialName;
    private String foodMaterialType;
    private String foodCategory;
    private String reason;

    private int disposalCount;
    private int totalDisposalPrice;
    private double reasonRatio;
    private LocalDate disposalDay;
    private double disposalRate;
}
