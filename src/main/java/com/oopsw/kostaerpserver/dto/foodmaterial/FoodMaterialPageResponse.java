package com.oopsw.kostaerpserver.dto.foodmaterial;

import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class FoodMaterialPageResponse {

    private List<FoodMaterialResponse> foodList;

    private int currentPage;
    private int totalPage;
    private int totalCount;
    private int pageSize;
    private String sort;
    private String keyword;
}