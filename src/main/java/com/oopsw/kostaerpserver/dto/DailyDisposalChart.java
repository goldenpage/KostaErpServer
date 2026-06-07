package com.oopsw.kostaerpserver.dto;

import java.util.List;
import java.util.Map;
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
