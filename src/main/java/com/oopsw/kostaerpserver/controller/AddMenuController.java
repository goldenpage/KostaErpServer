package com.oopsw.kostaerpserver.controller;

import com.oopsw.kostaerpserver.auth.ErpUserDetails;
import com.oopsw.kostaerpserver.dto.addmenu.GetMenuCategoryListResponse;
import com.oopsw.kostaerpserver.dto.statistics.StatisticsRequest;
import com.oopsw.kostaerpserver.service.Interface.AddMenuService;
import com.oopsw.kostaerpserver.vo.MenuCategory;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class AddMenuController {
    private final AddMenuService addMenuService;

    @GetMapping("/menuadd")
    public String addMenu(Model model,
                          @ModelAttribute StatisticsRequest statisticsRequest,
                          @AuthenticationPrincipal ErpUserDetails erpUserDetails) {
        String bId = erpUserDetails.getLoginUser().getBId();
        System.out.println(bId);
        List<MenuCategory> categoryList = addMenuService.getMenuCategoryList(bId);
        model.addAttribute("categoryList", GetMenuCategoryListResponse.fromList(categoryList));

        return "addMenu";
    }
}
