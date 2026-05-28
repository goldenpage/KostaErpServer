package com.oopsw.kostaerpserver.service;

import com.oopsw.kostaerpserver.vo.FoodCategory;
import com.oopsw.kostaerpserver.vo.FoodMaterial;

import java.util.List;
import java.util.Map;

public interface AddFoodMaterialService {
    // 1. 사용하고 있는 식자재 개수 조회
    int getFoodMaterialCount(String bId);

    // 2. 식자재 입력
    int addFoodMaterial(FoodMaterial foodMaterial);

    // 3. 카테고리 여부 체크
    int checkFoodCategoryExists(String foodCategory);

    // 4. 카테고리 추가
    int addFoodCategory(FoodCategory foodCategory);

    // 5. 카테고리 삭제
    int deleteFoodCategory(String foodCategory);

    // 6. 식자재 이름 검색
    List<FoodMaterial> getFoodMaterialByName(Map<String, Object> params);

    // 7. 식자재 목록 조회
    List<FoodMaterial> getFoodMaterialList(Map<String, Object> params);

    // 8. 식자재 상세 조회
    FoodMaterial getFoodMaterialDetail(String foodMaterialId);

    // 9. 식자재 삭제 전처리 - USED 삭제
    int deleteUsedByFoodMaterial(String foodMaterialId);

    // 10. 식자재 삭제 전처리 - DISPOSALS 삭제
    int deleteDisposalsByFoodMaterial(String foodMaterialId);

    // 11. 식자재 삭제
    int deleteFoodMaterial(Map<String, Object> params);

    // 12. 식자재 총 구매금액 조회
    int getFoodMaterialTotalAmount(Map<String, Object> params);

    // 13. 식자재 지출 랭킹 조회
    List<FoodMaterial> getFoodMaterialSpendingRank(Map<String, Object> params);

    // 14. 카테고리 전체 목록 조회
    List<FoodCategory> getFoodCategoryList();

    // 15. 카테고리에 식자재 존재 여부
    int hasFoodMaterialByCategory(String foodCategory);

    // 16. 식자재 전체 목록 조회
    List<FoodMaterial> getFoodMaterialListAll(String bId);

    // 17. 카테고리 ID 조회
    String getCategoryId(String foodCategory);
}
