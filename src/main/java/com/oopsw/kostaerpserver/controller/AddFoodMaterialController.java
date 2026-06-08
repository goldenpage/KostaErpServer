package com.oopsw.kostaerpserver.controller;

import com.oopsw.kostaerpserver.dto.addfoodmaterial.AddFoodMaterialRequest;
import com.oopsw.kostaerpserver.dto.addfoodmaterial.AddFoodMaterialResponse;
import com.oopsw.kostaerpserver.dto.addfoodmaterial.GetFoodCategoryListResponse;
import com.oopsw.kostaerpserver.service.Interface.AddFoodMaterialService;
import com.oopsw.kostaerpserver.vo.AddFoodMaterial;
import com.oopsw.kostaerpserver.vo.FoodCategory;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class AddFoodMaterialController {
    private final AddFoodMaterialService addFoodMaterialService;

    @GetMapping("/foodmaterial/add")
    public String addFoodMaterial(Model model, HttpSession session) {

        List<FoodCategory> categoryList = addFoodMaterialService.getFoodCategoryList();
        model.addAttribute("categoryList", GetFoodCategoryListResponse.fromList(categoryList));

        return "addFoodMaterial";
    }
}
