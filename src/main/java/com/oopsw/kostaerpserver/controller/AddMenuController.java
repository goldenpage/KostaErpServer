package com.oopsw.kostaerpserver.controller;

import com.oopsw.kostaerpserver.service.Interface.AddMenuService;
import com.oopsw.kostaerpserver.vo.AddMenu;
import com.oopsw.kostaerpserver.vo.MenuCategory;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class AddMenuController {
    private final AddMenuService addMenuService;

    @GetMapping("/menu/add")
    public String addMenu(Model model, HttpSession session) {
        List<MenuCategory> categoryList = addMenuService.getMenuCategoryList("0000000000");
        model.addAttribute("categoryList", categoryList);

        return "addMenu";
    }

    @PostMapping("/menu/add")
    public String addMenu(
            @RequestParam(value = "menuName") List<String> menuName,
            @RequestParam(value = "menuPrice") List<String> menuPrice,
            @RequestParam(value = "menuCategoryId") List<String> menuCategoryId,
            HttpSession session,
            RedirectAttributes redirectAttributes){

        try{
            List<AddMenu> list = new ArrayList<>();
            for(int i = 0; i < menuName.size(); i++){
                AddMenu vo = new AddMenu();
                vo.setMenuName(menuName.get(i));
                vo.setMenuName(menuPrice.get(i));
                vo.setMenuName(menuCategoryId.get(i));
                list.add(vo);
            }
            for(AddMenu vo : list){
                addMenuService.addMenu(vo);
            }
            redirectAttributes.addFlashAttribute("success", "메뉴 등록 성공");
        }catch (Exception e){
            redirectAttributes.addFlashAttribute("errorMessage", "메뉴 등록 실패");
        }
        return "redirect:/menu/add";
    }
}
