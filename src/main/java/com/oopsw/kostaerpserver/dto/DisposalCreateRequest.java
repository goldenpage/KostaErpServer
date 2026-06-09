package com.oopsw.kostaerpserver.dto;

import com.oopsw.kostaerpserver.vo.Disposal;
import lombok.Data;

import java.time.LocalDate;

@Data
public class DisposalCreateRequest {
    private String disposalId;
    private int disposalCountAll;
    private int disposalPrice;
    private LocalDate disposalDate;
    private String foodMaterialId;
    private String reasonId;
}
