package com.oopsw.kostaerpserver.dto.statistics;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DailyDisposalChart {
    private String foodMaterialType;
    private String disposalDay;
    private int disposalCount;
    private int totalDisposalPrice;


}
