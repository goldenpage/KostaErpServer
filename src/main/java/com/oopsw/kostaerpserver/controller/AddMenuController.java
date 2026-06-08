package com.oopsw.kostaerpserver.controller;

import com.oopsw.kostaerpserver.dto.addmenu.AddMenuRequest;
import com.oopsw.kostaerpserver.dto.addmenu.AddMenuResponse;
import com.oopsw.kostaerpserver.dto.addmenu.GetMenuCategoryListResponse;
import com.oopsw.kostaerpserver.service.Interface.AddMenuService;
import com.oopsw.kostaerpserver.vo.AddMenu;
import com.oopsw.kostaerpserver.vo.MenuCategory;
import com.oopsw.kostaerpserver.vo.Used;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class AddMenuController {
    private final AddMenuService addMenuService;

    @GetMapping("/menu/add")
    public String addMenu(Model model, HttpSession session) {
        String bId = "0000000000";
//                (String) session.getAttribute("loginOK");

        List<MenuCategory> categoryList = addMenuService.getMenuCategoryList("0000000000");
        model.addAttribute("categoryList", GetMenuCategoryListResponse.fromList(categoryList));

        return "addMenu";
    }

    @PostMapping("/menu/add")
    @ResponseBody
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
