package com.oopsw.kostaerpserver.service;

import com.oopsw.kostaerpserver.repository.AddFoodMaterialDAO;
import com.oopsw.kostaerpserver.service.Interface.AddFoodMaterialService;
import com.oopsw.kostaerpserver.service.entity.purchase.PurchaseConvert;
import com.oopsw.kostaerpserver.service.entity.purchase.PurchaseService;
import com.oopsw.kostaerpserver.vo.AddFoodMaterial;
import com.oopsw.kostaerpserver.vo.FoodCategory;
import com.oopsw.kostaerpserver.vo.FoodMaterial;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@Slf4j
@RequiredArgsConstructor
public class AddFoodMaterialServiceImpl implements AddFoodMaterialService {
    private final AddFoodMaterialDAO addFoodMaterialDAO;
    private final PurchaseService purchaseService;

    // 1. 사용하고 있는 식자재 개수 조회
    public int getFoodMaterialCount(String bId){
        return addFoodMaterialDAO.getFoodMaterialCount(bId);
    }

    // 2. 식자재 입력
    public int addFoodMaterial(AddFoodMaterial addFoodMaterial){
        int result = addFoodMaterialDAO.addFoodMaterial(addFoodMaterial);
        log.info("addFoodMaterial result = {}, addFoodMaterial = {}", result, addFoodMaterial);

        if (result > 0) {
            boolean purchaseSaved = purchaseService.addPurchase(PurchaseConvert.from(addFoodMaterial));
            log.info("purchase saved = {}", purchaseSaved);
        }
        return result;
    }

    // 3. 카테고리 여부 체크
    public int checkFoodCategoryExists(String foodCategory){
        return addFoodMaterialDAO.checkFoodCategoryExists(foodCategory);
    }

    // 4. 카테고리 추가
    public int addFoodCategory(FoodCategory foodCategory){
        return addFoodMaterialDAO.addFoodCategory(foodCategory);
    }

    // 5. 카테고리 삭제
    public int deleteFoodCategory(String foodCategory){
        return addFoodMaterialDAO.deleteFoodCategory(foodCategory);
    }

    // 6. 식자재 이름 검색
    public List<FoodMaterial> getFoodMaterialByName(String foodMaterialName, String bId){
        return addFoodMaterialDAO.getFoodMaterialByName(foodMaterialName, bId);
    }

    // 7. 식자재 목록 조회
    public List<FoodMaterial> getFoodMaterialList(Map<String, Object> params){
        return addFoodMaterialDAO.getFoodMaterialList(params);
    }

    // 8. 식자재 상세 조회
    public FoodMaterial getFoodMaterialDetail(String foodMaterialId){
        return addFoodMaterialDAO.getFoodMaterialDetail(foodMaterialId);
    }

    // 9. 식자재 삭제 전처리 - USED 삭제
    public int deleteUsedByFoodMaterial(String foodMaterialId){
        return addFoodMaterialDAO.deleteUsedByFoodMaterial(foodMaterialId);
    }

    // 10. 식자재 삭제 전처리 - DISPOSALS 삭제
    public int deleteDisposalsByFoodMaterial(String foodMaterialId){
        return addFoodMaterialDAO.deleteDisposalsByFoodMaterial(foodMaterialId);
    }

    // 11. 식자재 삭제
    public int deleteFoodMaterial(Map<String, Object> params){
        return addFoodMaterialDAO.deleteFoodMaterial(params);
    }

    // 12. 식자재 총 구매금액 조회
    public int getFoodMaterialTotalAmount(Map<String, Object> params){
        return addFoodMaterialDAO.getFoodMaterialTotalAmount(params);
    }

    // 13. 식자재 지출 랭킹 조회
    public List<FoodMaterial> getFoodMaterialSpendingRank(Map<String, Object> params){
        return addFoodMaterialDAO.getFoodMaterialSpendingRank(params);
    }

    // 14. 카테고리 전체 목록 조회
    public List<FoodCategory> getFoodCategoryList(){
        return addFoodMaterialDAO.getFoodCategoryList();
    }

    // 15. 카테고리에 식자재 존재 여부
    public int hasFoodMaterialByCategory(String foodCategory){
        return addFoodMaterialDAO.hasFoodMaterialByCategory(foodCategory);
    }

    // 16. 식자재 전체 목록 조회
    public List<FoodMaterial> getFoodMaterialListAll(String bId){
        return addFoodMaterialDAO.getFoodMaterialListAll(bId);
    }

    // 17. 카테고리 ID 조회
    public String getCategoryId(String foodCategory){
        return addFoodMaterialDAO.getCategoryId(foodCategory);
    }

}
