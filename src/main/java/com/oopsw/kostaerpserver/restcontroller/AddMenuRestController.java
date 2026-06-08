package com.oopsw.kostaerpserver.restcontroller;

import com.oopsw.kostaerpserver.dto.addmenu.*;
import com.oopsw.kostaerpserver.service.Interface.AddFoodMaterialService;
import com.oopsw.kostaerpserver.service.Interface.AddMenuService;
import com.oopsw.kostaerpserver.vo.AddMenu;
import com.oopsw.kostaerpserver.vo.FoodMaterial;
import com.oopsw.kostaerpserver.vo.MenuCategory;
import com.oopsw.kostaerpserver.vo.Used;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
    public AddMenuCategoryResponse addMenuCategory(@RequestBody AddMenuCategoryRequest request, HttpSession session) {

        String bId = "0000000000";
        // (String) session.getAttribute("loginOK");

        try{
            MenuCategory vo = request.toVO(bId);
            int result = addMenuService.checkMenuCategoryExists(vo);

            if(result > 0){
                return AddMenuCategoryResponse.fail("이미 존재하는 카테고리");
            }

            addMenuService.addMenuCategory(vo);
            String categoryId = addMenuService.getCategoryId(request.getMenuCategory());

            return AddMenuCategoryResponse.success(categoryId, request.getMenuCategory());

        } catch (Exception e) {
            return AddMenuCategoryResponse.fail("카테고리 추가 실패");
        }
    }

    @DeleteMapping("/menu/menucategory/delete")
    public DeleteMenuCategoryResponse deleteMenuCategory(@RequestBody DeleteMenuCategoryRequest request) {

        try{
            int exist = addMenuService.hasMenuByCategory(request.getMenuCategory());
            if(exist > 0){
                return DeleteMenuCategoryResponse.fail("사용중인 카테고리");
            }

            addMenuService.deleteMenuCategory(request.getMenuCategory());
            return DeleteMenuCategoryResponse.success(request.getMenuCategory());

        }catch (Exception e){
            return DeleteMenuCategoryResponse.fail("카테고리 삭제 오류");
        }
    }

    @GetMapping("/menu/foodmaterial/list")
    public List<GetFoodMaterialListResponse> getFoodMaterialList(HttpSession session) {
        String bId = "0000000000";
        return GetFoodMaterialListResponse.fromList(addFoodMaterialService.getFoodMaterialListAll(bId));
    }

    @PostMapping("/menu/add")
    public AddMenuResponse addMenu(
            AddMenuRequest request,
            HttpSession session,
            RedirectAttributes redirectAttributes){

        String bId = "0000000000";
//                (String) session.getAttribute("loginOK");

        try{
            int menuCount = 0;

            for(int i = 0; i < request.getMenuName().size(); i++){
                AddMenu vo = new AddMenu();
                vo.setMenuName(request.getMenuName().get(i));
                vo.setMenuPrice(Integer.parseInt(request.getMenuPrice().get(i)));
                vo.setMenuCategoryId(request.getMenuCategoryId().get(i));
                addMenuService.addMenu(vo);

                String menuId = addMenuService.getNewMenuId(vo);
                int ingredientCount = Integer.parseInt((request.getMenuIngredientCount().get(i)));

                for(int j = 0; j < ingredientCount; j++){
                    Used used = new Used();
                    used.setUsedCount(Integer.parseInt(request.getUsedCount().get(menuCount)));
                    used.setFoodMaterialId(request.getFoodMaterialId().get(menuCount));
                    used.setMenuId(menuId);
                    addMenuService.addUsedMaterial(used);

                    menuCount++;
                }
            }
            return AddMenuResponse.success(request.getMenuName().size());
        }catch (Exception e){
            return AddMenuResponse.fail("메뉴 등록 실패");
        }
    }
}
