package com.oopsw.kostaerpserver.repository;

import com.oopsw.kostaerpserver.vo.FoodCategory;
import com.oopsw.kostaerpserver.vo.FoodMaterial;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface AddFoodMaterialDAO {
    /*
    List<Integer> getFoodMaterialCount();
    List<String> addFoodMaterial();
    List<Integer> checkFoodCategoryExists();
    List<String> addFoodCategory();
    List<Integer> deleteFoodMaterialByCategory();
    List<String> deleteFoodCategory();
    List<Integer> getFoodMaterialByName();
    List<String> getFoodMaterialList();
    List<Integer> getFoodMaterialDetail();
    List<String> deleteUsedByFoodMaterial();
    List<Integer> deleteDisposalsByFoodMaterial();
    List<String> deleteFoodMaterial();
    List<String> getFoodMaterialTotalAmount();
    List<String> getFoodMaterialSpendingRank();
    List<String> getFoodCategoryList();
    List<String> hasFoodMaterialByCategory();
    List<String> getFoodMaterialListAll();
    List<String> getCategoryId();
     */

    // 식자재 총 개수 조회
    int getFoodMaterialCount(String bId);

    // 식자재 단건 INSERT (Service에서 루프 호출 → @Transactional 처리)
    int addFoodMaterial(FoodMaterial foodMaterial);

    // 카테고리 존재 여부 확인 (0이면 없음)
    int checkFoodCategoryExists(String foodCategory);

    // 카테고리 추가
    int addFoodCategory(String foodCategory);

    // 카테고리에 속한 식자재 삭제 (카테고리 삭제 전처리)
    int deleteFoodMaterialByCategory(String foodCategory);

    // 카테고리 삭제
    int deleteFoodCategory(String foodCategory);

    // 식자재 이름 검색 (LIKE)
    List<FoodMaterial> getFoodMaterialByName(Map<String, Object> params);

    // 식자재 목록 조회 (페이징, 정렬)
    // params: bId, orderBy, offset, pageSize
    List<FoodMaterial> getFoodMaterialList(Map<String, Object> params);

    // 식자재 상세 조회
    FoodMaterial getFoodMaterialDetail(String foodMaterialId);

    // 식자재 삭제 전처리 - USED 삭제
    int deleteUsedByFoodMaterial(String foodMaterialId);

    // 식자재 삭제 전처리 - DISPOSALS 삭제
    int deleteDisposalsByFoodMaterial(String foodMaterialId);

    // 식자재 삭제
    // params: foodMaterialId, bId
    int deleteFoodMaterial(Map<String, Object> params);

    // 식자재 총 구매금액 조회
    // params: bId, startDate, endDate
    int getFoodMaterialTotalAmount(Map<String, Object> params);

    // 식자재 지출 랭킹 조회
    // params: bId, startDate, endDate
    List<FoodMaterial> getFoodMaterialSpendingRank(Map<String, Object> params);

    // 카테고리 전체 목록 조회
    List<FoodCategory> getFoodCategoryList();

    // 해당 카테고리에 식자재 존재 여부 (0이면 없음)
    int hasFoodMaterialByCategory(String foodCategory);

    // 식자재 전체 목록 조회 (메뉴 등록 시 사용)
    List<FoodMaterial> getFoodMaterialListAll(String bId);

    // 카테고리명으로 카테고리 ID 조회
    String getCategoryId(String foodCategory);

}
