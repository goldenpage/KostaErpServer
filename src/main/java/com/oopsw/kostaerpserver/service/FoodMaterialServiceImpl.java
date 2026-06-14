package com.oopsw.kostaerpserver.service;

import com.oopsw.kostaerpserver.dto.foodmaterial.FoodMaterialPageResponse;
import com.oopsw.kostaerpserver.dto.foodmaterial.FoodMaterialResponse;
import com.oopsw.kostaerpserver.dto.foodmaterial.FoodMaterialSearchRequest;
import com.oopsw.kostaerpserver.repository.dao.FoodMaterialDAO;
import com.oopsw.kostaerpserver.service.Interface.FoodMaterialService;
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

    @Override
    public FoodMaterialPageResponse getFoodMaterialPage(FoodMaterialSearchRequest request) {
        String bId = request.getBId();
        String sort = request.getSort();
        int page = request.getPage();
        int size = request.getSize();
        String keyword = request.getKeyword();

        if (bId == null || bId.isBlank()) {
            bId = "0000000000";
        }

        if (sort == null || sort.isBlank()) {
            sort = "idDesc";
        }

        if (page < 1) {
            page = 1;
        }

        if (size < 1) {
            size = 5;
        }

        List<FoodMaterial> foodVoList;
        int totalCount;

        if (keyword != null && !keyword.isBlank()) {
            List<FoodMaterial> searchResult = searchFoodMaterial(bId, keyword);
            totalCount = searchResult.size();

            int totalPage = calculateTotalPage(totalCount, size);

            if (page > totalPage) {
                page = totalPage;
            }

            int fromIndex = Math.min((page - 1) * size, totalCount);
            int toIndex = Math.min(fromIndex + size, totalCount);

            foodVoList = searchResult.subList(fromIndex, toIndex);
        } else {
            totalCount = getFoodMaterialCount(bId);
            int totalPage = calculateTotalPage(totalCount, size);

            if (page > totalPage) {
                page = totalPage;
            }

            foodVoList = getFoodMaterialList(bId, sort, page, size);
        }

        List<FoodMaterialResponse> foodList = foodVoList.stream()
                .map(FoodMaterialResponse::from)
                .toList();

        int totalPage = calculateTotalPage(totalCount, size);

        return FoodMaterialPageResponse.builder()
                .foodList(foodList)
                .currentPage(page)
                .totalPage(totalPage)
                .totalCount(totalCount)
                .pageSize(size)
                .sort(sort)
                .keyword(keyword)
                .build();
    }

    private int calculateTotalPage(int totalCount, int size) {
        int totalPage = (int) Math.ceil((double) totalCount / size);

        if (totalPage < 1) {
            totalPage = 1;
        }

        return totalPage;
    }

}