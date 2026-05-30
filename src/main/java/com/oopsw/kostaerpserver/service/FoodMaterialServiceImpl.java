package com.oopsw.kostaerpserver.service;

import com.oopsw.kostaerpserver.repository.FoodMaterialDAO;
import com.oopsw.kostaerpserver.vo.FoodMaterial;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FoodMaterialServiceImpl implements FoodMaterialService {

    private final FoodMaterialDAO foodMaterialDAO;

    @Override
    public int getFoodMaterialCount(String bId) {
        return foodMaterialDAO.getFoodMaterialCount(bId);
    }

    @Override
    public List<FoodMaterial> getFoodMaterialList(String bId, String sortType, int page, int pageSize) {
        if (sortType == null || sortType.isBlank()) {
            sortType = "idDesc";
        }

        if (page < 1) {
            page = 1;
        }

        if (pageSize < 1) {
            pageSize = 5;
        }

        int offset = (page - 1) * pageSize;

        switch (sortType) {
            case "idAsc":
                return foodMaterialDAO.getFoodMaterialListIdAsc(bId, pageSize, offset);

            case "expAsc":
                return foodMaterialDAO.getFoodMaterialListExpAsc(bId, pageSize, offset);

            case "expDesc":
                return foodMaterialDAO.getFoodMaterialListExpDesc(bId, pageSize, offset);

            case "idDesc":
            default:
                return foodMaterialDAO.getFoodMaterialListIdDesc(bId, pageSize, offset);
        }
    }

    @Override
    public List<FoodMaterial> searchFoodMaterial(String bId, String foodMaterialName) {
        return foodMaterialDAO.getFoodMaterialByName(bId, foodMaterialName);
    }

    @Override
    @Transactional
    public void deleteFoodMaterial(String foodMaterialId, String bId) {
        foodMaterialDAO.deleteUsedByFoodMaterial(foodMaterialId);
        foodMaterialDAO.deleteDisposalsByFoodMaterial(foodMaterialId);

        int result = foodMaterialDAO.deleteFoodMaterial(foodMaterialId, bId);

        if (result == 0) {
            throw new RuntimeException("삭제 실패");
        }
    }
}