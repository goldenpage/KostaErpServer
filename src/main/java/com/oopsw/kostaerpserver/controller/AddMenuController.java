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
}
