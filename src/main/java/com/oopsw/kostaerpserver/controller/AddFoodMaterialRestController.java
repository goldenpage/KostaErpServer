package com.oopsw.kostaerpserver.controller;

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
    public Map<String, Object> addFoodCategory(@RequestBody Map<String, String> body) {
        String foodCategory = body.get("foodCategory");
        Map<String, Object> response = new HashMap<>();
        try {
            int result = addFoodMaterialService.checkFoodCategoryExists(foodCategory);
            if (result > 0) {
                response.put("result", "fail");
                response.put("message", "이미 존재하는 카테고리");
                return response;
            }

            FoodCategory vo = new FoodCategory();
            vo.setFoodCategory(foodCategory);
            addFoodMaterialService.addFoodCategory(vo);

            String categoryId = addFoodMaterialService.getCategoryId(foodCategory);

            response.put("result", "success");
            response.put("foodCategoryId", categoryId);
            response.put("foodCategory", foodCategory);

        } catch (Exception e) {
            response.put("result", "fail");
            response.put("message", "카테고리 추가 실패");
        }
        return response;
    }

    @DeleteMapping("/foodmaterial/foodcategory/delete")
    public Map<String, Object> deleteFoodCategory(@RequestBody Map<String, String> body) {
        String foodCategory = body.get("foodCategory");
        Map<String, Object> response = new HashMap<>();

        try{
            int exist = addFoodMaterialService.hasFoodMaterialByCategory(foodCategory);
            if (exist > 0) {
                response.put("result", "fail");
                response.put("message", "해당 카테고리가 사용중입니다.");
                return response;
            }

            int result = addFoodMaterialService.deleteFoodCategory(foodCategory);
            response.put("result", "success");
            response.put("foodCategoryId", foodCategory + "가 삭제되었습니다.");
        }catch (Exception e){
            response.put("result", "fail");
            response.put("message", "카테고리 삭제 오류");
        }
        return response;
    }

    @GetMapping("/foodmaterial/search/add/{foodMaterialName}")
    public List<FoodMaterial> searchFoodMaterial(
            @PathVariable String foodMaterialName, HttpSession session){
        Map<String, Object> response = new HashMap<>();
        response.put("foodMaterialName", foodMaterialName);
        response.put("0000000000", session.getAttribute("loginOK"));
        return addFoodMaterialService.getFoodMaterialByName(response);
    }
}
