package com.oopsw.kostaerpserver.restcontroller;

import com.oopsw.kostaerpserver.auth.ErpUserDetails;
import com.oopsw.kostaerpserver.dto.addmenu.*;
import com.oopsw.kostaerpserver.dto.statistics.StatisticsRequest;
import com.oopsw.kostaerpserver.service.Interface.AddFoodMaterialService;
import com.oopsw.kostaerpserver.service.Interface.AddMenuService;
import com.oopsw.kostaerpserver.vo.AddMenu;
import com.oopsw.kostaerpserver.vo.MenuCategory;
import com.oopsw.kostaerpserver.vo.Used;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class AddMenuRestController {
    private final AddMenuService addMenuService;
    private final AddFoodMaterialService addFoodMaterialService;

    @PostMapping("/menu/menucategory/add")
    public AddMenuCategoryResponse addMenuCategory(
            @RequestBody AddMenuCategoryRequest request,
            @ModelAttribute StatisticsRequest statisticsRequest,
            @AuthenticationPrincipal ErpUserDetails erpUserDetails) {

        String bId = erpUserDetails.getLoginUser().getBId();

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
    public List<GetFoodMaterialListResponse> getFoodMaterialList(
            @ModelAttribute StatisticsRequest statisticsRequest,
            @AuthenticationPrincipal ErpUserDetails erpUserDetails) {
        String bId = erpUserDetails.getLoginUser().getBId();
        return GetFoodMaterialListResponse.fromList(addFoodMaterialService.getFoodMaterialListAll(bId));
    }

    @PostMapping("/menu/add")
    public AddMenuResponse addMenu(
            AddMenuRequest request,
            @ModelAttribute StatisticsRequest statisticsRequest,
            @AuthenticationPrincipal ErpUserDetails erpUserDetails){

//        String bId = erpUserDetails.getLoginUser().getBId();

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
