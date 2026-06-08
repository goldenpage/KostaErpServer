package com.oopsw.kostaerpserver.restcontroller;

import com.oopsw.kostaerpserver.dto.addfoodmaterial.*;
import com.oopsw.kostaerpserver.service.Interface.AddFoodMaterialService;
import com.oopsw.kostaerpserver.vo.FoodCategory;
import com.oopsw.kostaerpserver.vo.FoodMaterial;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class AddFoodMaterialRestController {
    private final AddFoodMaterialService addFoodMaterialService;

    @PostMapping("/foodmaterial/foodcategory/add")
    public FoodCategoryResponse addFoodCategory(@RequestBody AddFoodCategoryRequest request) {
        try {
            int result = addFoodMaterialService.checkFoodCategoryExists(request.getFoodCategory());
            if (result > 0) {
                return FoodCategoryResponse.fail("이미 존재하는 카테고리입니다.");
            }

            FoodCategory vo = new FoodCategory();
            vo.setFoodCategory(request.getFoodCategory());
            addFoodMaterialService.addFoodCategory(vo);

            String categoryId = addFoodMaterialService.getCategoryId(request.getFoodCategory());
            return FoodCategoryResponse.success(categoryId, request.getFoodCategory());

        } catch (Exception e) {
            return FoodCategoryResponse.fail("카테고리 추가 실패");
        }
    }

    @DeleteMapping("/foodmaterial/foodcategory/delete")
    public CommonResponse deleteFoodCategory(@RequestBody DeleteFoodCategoryRequest request) {
        try{
            int exist = addFoodMaterialService.hasFoodMaterialByCategory(request.getFoodCategory());
            if (exist > 0) {
                return CommonResponse.fail("사용하고 있는 카테고리입니다.");
            }

            addFoodMaterialService.deleteFoodCategory(request.getFoodCategory());
            return CommonResponse.success(request.getFoodCategory() + "가 삭제되었습니다.");

        }catch (Exception e){
            return CommonResponse.fail("카테고리 삭제에 실패했습니다.");
        }
    }

    @GetMapping("/foodmaterial/search/add/{foodMaterialName}")
    public List<FoodMaterial> searchFoodMaterial(
            @PathVariable String foodMaterialName, HttpSession session){
        Map<String, Object> response = new HashMap<>();
        response.put("foodMaterialName", foodMaterialName);
        response.put("bId", "0000000000");
        return addFoodMaterialService.getFoodMaterialByName(response);
    }
}
