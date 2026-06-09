package com.oopsw.kostaerpserver.controller;

import com.oopsw.kostaerpserver.auth.ErpUserDetails;
import com.oopsw.kostaerpserver.service.Interface.MenuService;
import com.oopsw.kostaerpserver.vo.Menu;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class MenuController {

    private final MenuService menuService;

    @GetMapping("/menus")
    public String getMenus(
            @AuthenticationPrincipal ErpUserDetails userDetails,
            Model model
    ) {
        if (userDetails == null) {
            return "redirect:/login";
        }

        String bId = userDetails.getUsername();

        List<Menu> menuList = menuService.getMenuList(bId);

        String selectedMenuId = "";

        if (!menuList.isEmpty()) {
            selectedMenuId = menuList.get(0).getMenuId();
        }

        model.addAttribute("menuList", menuList);
        model.addAttribute("selectedMenuId", selectedMenuId);

        return "menuList";
    }
}