package com.oopsw.kostaerpserver.service.Interface;

import com.oopsw.kostaerpserver.vo.FoodMaterial;

import java.util.List;

public interface FoodMaterialService {

    int getFoodMaterialCount(String bId);

    List<FoodMaterial> getFoodMaterialList(String bId, String sortType, int page, int pageSize);

    List<FoodMaterial> searchFoodMaterial(String bId, String foodMaterialName);

    void deleteFoodMaterial(String foodMaterialId, String bId);
}