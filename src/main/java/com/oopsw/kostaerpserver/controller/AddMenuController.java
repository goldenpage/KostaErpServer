package com.oopsw.kostaerpserver.controller;

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
        model.addAttribute("categoryList", categoryList);

        return "addMenu";
    }

    @PostMapping("/menu/add")
    @ResponseBody
    public Map<String, Object> addMenu(
            @RequestParam(value = "menuName") List<String> menuName,
            @RequestParam(value = "menuPrice") List<String> menuPrice,
            @RequestParam(value = "menuCategoryId") List<String> menuCategoryId,
            @RequestParam(value = "menuIngredientCount") List<String> menuIngredientCount,
            @RequestParam(value = "foodMaterialId") List<String> foodMaterialId,
            @RequestParam(value = "usedCount") List<String> usedCount,
            HttpSession session,
            RedirectAttributes redirectAttributes){

        Map<String, Object> response = new HashMap<>();
        String bId = "0000000000";
//                (String) session.getAttribute("loginOK");

        try{
            for(int i=0; i < menuName.size(); i++){
                String existing = addMenuService.hasMenuCheck(menuName.get(i));
                if(existing != null){
                    response.put("result", "fail");
                    redirectAttributes.addFlashAttribute("message", menuName.get(i) + "는 이미 존재하는 메뉴입니다.");
                    return response;
                }
            }

            int menuCount = 0;

            for(int i = 0; i < menuName.size(); i++){
                AddMenu vo = new AddMenu();
                vo.setMenuName(menuName.get(i));
                vo.setMenuPrice(Integer.parseInt(menuPrice.get(i)));
                vo.setMenuCategoryId(menuCategoryId.get(i));
                addMenuService.addMenu(vo);

                String menuId = addMenuService.getNewMenuId(vo);
                int ingredientCount = Integer.parseInt((menuIngredientCount.get(i)));

                for(int j = 0; j < ingredientCount; j++){
                    Used used = new Used();
                    used.setUsedCount(Integer.parseInt(usedCount.get(menuCount)));
                    used.setFoodMaterialId(foodMaterialId.get(menuCount));
                    used.setMenuId(menuId);
                    addMenuService.addUsedMaterial(used);

                    menuCount++;
                }
            }
            response.put("result", "success");
            response.put("message", menuName.size() + "개의 메뉴 등록 성공");
        }catch (Exception e){
            response.put("result", "fail");
            response.put("message", "메뉴 등록 실패");
        }
        return response;
    }
}
