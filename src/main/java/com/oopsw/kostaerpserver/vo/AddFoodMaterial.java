package com.oopsw.kostaerpserver.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddFoodMaterial {
    private String foodMaterialId;
    private String foodMaterialName;
    private String foodCategory_Id;
    private int foodMaterialCount;
    private int foodMaterialCountAll;
    private int foodMaterialPrice;
    private String vender;
    private String foodMaterialType;
    private Date incomeDate;
    private Date expirationDate;
    private String bId;
}
