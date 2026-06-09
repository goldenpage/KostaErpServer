package com.oopsw.kostaerpserver.controller;

import com.oopsw.kostaerpserver.dto.addfoodmaterial.GetFoodCategoryListResponse;
import com.oopsw.kostaerpserver.service.Interface.AddFoodMaterialService;
import com.oopsw.kostaerpserver.vo.FoodCategory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class AddFoodMaterialController {
    private final AddFoodMaterialService addFoodMaterialService;

    @GetMapping("/foodmaterialadd")
    public String addFoodMaterial(Model model) {

        List<FoodCategory> categoryList = addFoodMaterialService.getFoodCategoryList();
        model.addAttribute("categoryList", GetFoodCategoryListResponse.fromList(categoryList));

        return "addFoodMaterial";
    }
}
