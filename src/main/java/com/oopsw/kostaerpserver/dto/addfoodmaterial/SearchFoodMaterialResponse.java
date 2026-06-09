package com.oopsw.kostaerpserver.dto.addfoodmaterial;

import com.oopsw.kostaerpserver.vo.FoodMaterial;
import lombok.*;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SearchFoodMaterialResponse {
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

    public static SearchFoodMaterialResponse from(FoodMaterial vo) {
        return SearchFoodMaterialResponse.builder()
                .foodMaterialId(vo.getFoodMaterialId())
                .foodMaterialName(vo.getFoodMaterialName())
                .foodCategory(vo.getFoodCategory())
                .foodMaterialCount(vo.getFoodMaterialCount())
                .foodMaterialCountAll(vo.getFoodMaterialCountAll())
                .foodMaterialPrice(vo.getFoodMaterialPrice())
                .vender(vo.getVender())
                .foodMaterialType(vo.getFoodMaterialType())
                .incomeDate(vo.getIncomeDate())
                .expirationDate(vo.getExpirationDate())
                .build();
    }

    public static List<SearchFoodMaterialResponse> fromList(List<FoodMaterial> voList) {
        return voList.stream()
                .map(SearchFoodMaterialResponse::from)
                .collect(Collectors.toList());
    }
}
