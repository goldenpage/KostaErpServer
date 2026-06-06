package com.oopsw.kostaerpserver.controller;

import com.oopsw.kostaerpserver.service.Interface.AddMenuService;
import com.oopsw.kostaerpserver.vo.FoodMaterial;
import com.oopsw.kostaerpserver.vo.MenuCategory;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class AddMenuRestController {
    private final AddMenuService addMenuService;

    @PostMapping("/menu/menucategory/add")
    public Map<String, Object> addMenuCategory(@RequestBody Map<String, String> body) {
        String menuCategory = body.get("menuCategory");
        Map<String, Object> response = new HashMap<>();
        try{
            int result = addMenuService.checkMenuCategoryExists(menuCategory);
            if(result > 0){
                response.put("result", "fail");
                response.put("message", "이미 존재하는 카테고리");
                return response;
            }
            MenuCategory vo = new MenuCategory();
            vo.setMenuCategory(menuCategory);
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
            int exist = addMenuService.deleteMenuCategory(menuCategory);
            if(exist > 0){
                response.put("result", "fail");
                response.put("message", "사용중인 카테고리");
                 return response;
            }

            int result = addMenuService.deleteMenuCategory(menuCategory);
            response.put("result", "success");
            response.put("menuCategoryId", menuCategory + "가 삭제되었습니다.");
        }catch (Exception e){
            response.put("result", "fail");
            response.put("message", "카테고리 삭제 오류");
        }
        return response;
    }

}
