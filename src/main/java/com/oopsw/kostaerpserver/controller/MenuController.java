package com.oopsw.kostaerpserver.controller;

import com.oopsw.kostaerpserver.service.Interface.MenuService;
import com.oopsw.kostaerpserver.vo.Menu;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class MenuController {

    private final MenuService menuService;

    @GetMapping("/menus")
    public String getMenus(
            @RequestParam(defaultValue = "0000000000") String bId,
            Model model
    ) {
        List<Menu> menuList = menuService.getMenuList(bId);

        String selectedMenuId = "";
        if (!menuList.isEmpty()) {
            selectedMenuId = menuList.get(0).getMenuId();
        }

        model.addAttribute("menuList", menuList);
        model.addAttribute("selectedMenuId", selectedMenuId);
        model.addAttribute("bId", bId);

        return "menuList";
    }
}