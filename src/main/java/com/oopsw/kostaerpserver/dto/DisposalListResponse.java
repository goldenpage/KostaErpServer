package com.oopsw.kostaerpserver.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DisposalListResponse {
    private String disposalId;
    private int disposalCountAll;
    private int disposalPrice;
    private LocalDate disposalDate;

    private String foodMaterialName;
    private String foodMaterialType;
    private String foodCategory;

    private String reason;
}
