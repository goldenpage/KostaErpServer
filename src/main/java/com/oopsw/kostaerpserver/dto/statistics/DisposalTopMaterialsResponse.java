package com.oopsw.kostaerpserver.dto.statistics;



import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class DisposalTopMaterialsResponse {
    private String foodMaterialId;
    private String foodMaterialName;
    private int disposalCount;
    private int totalDisposalPrice;
}
