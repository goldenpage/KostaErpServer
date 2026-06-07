package com.oopsw.kostaerpserver.controller.restcontroller;

import com.oopsw.kostaerpserver.service.Interface.AddFoodMaterialService;
import com.oopsw.kostaerpserver.service.Interface.AddMenuService;
import com.oopsw.kostaerpserver.vo.FoodMaterial;
import com.oopsw.kostaerpserver.vo.MenuCategory;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class AddMenuRestController {
    private final AddMenuService addMenuService;
    private final AddFoodMaterialService addFoodMaterialService;

    @PostMapping("/menu/menucategory/add")
    public Map<String, Object> addMenuCategory(@RequestBody Map<String, String> body) {
        String menuCategory = body.get("menuCategory");
        Map<String, Object> response = new HashMap<>();
        try{
            MenuCategory vo = new MenuCategory();
            vo.setMenuCategory(menuCategory);
            vo.setBId("0000000000");
            int result = addMenuService.checkMenuCategoryExists(vo);

            if(result > 0){
                response.put("result", "fail");
                response.put("message", "이미 존재하는 카테고리");
                return response;
            }

            addMenuService.addMenuCategory(vo);
            String categoryId = addMenuService.getCategoryId(menuCategory);

            response.put("result", "success");
            response.put("menuCategoryId", categoryId);
            response.put("menuCategory", menuCategory);

        } catch (Exception e) {
            response.put("result", "fail");
            response.put("message", "카테고리 추가 실패");
        }
        return response;
    }

    @DeleteMapping("/menu/menucategory/delete")
    public Map<String, Object> deleteMenuCategory(@RequestBody Map<String, String> body) {
        String menuCategory = body.get("menuCategory");
        Map<String, Object> response = new HashMap<>();

        try{
            int exist = addMenuService.hasMenuByCategory(menuCategory);
            if(exist > 0){
                response.put("result", "fail");
                response.put("message", "사용중인 카테고리");
                 return response;
            }

            addMenuService.deleteMenuCategory(menuCategory);

            response.put("result", "success");
            response.put("menuCategoryId", menuCategory + "가 삭제되었습니다.");

        }catch (Exception e){
            response.put("result", "fail");
            response.put("message", "카테고리 삭제 오류");
        }
        return response;
    }

    @GetMapping("/menu/foodmaterial/list")
    public List<FoodMaterial> getFoodMaterialList(HttpSession session) {
        String bId = "0000000000";
        return addFoodMaterialService.getFoodMaterialListAll(bId);
    }
}
