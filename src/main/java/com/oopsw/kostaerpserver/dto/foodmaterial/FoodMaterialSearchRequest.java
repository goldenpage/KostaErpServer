package com.oopsw.kostaerpserver.dto.foodmaterial;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class FoodMaterialSearchRequest {

    private String bId = "0000000000";
    private String sort = "idDesc";
    private int page = 1;
    private int size = 5;
    private String keyword;
}