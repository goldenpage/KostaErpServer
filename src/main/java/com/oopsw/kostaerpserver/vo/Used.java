package com.oopsw.kostaerpserver.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Used {
    private String usedMaterialId;
    private int usedCount;
    private String foodMaterialId;
    private String menuId;
}
