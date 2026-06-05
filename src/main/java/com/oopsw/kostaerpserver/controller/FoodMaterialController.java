package com.oopsw.kostaerpserver.controller;

import com.oopsw.kostaerpserver.service.Interface.FoodMaterialService;
import com.oopsw.kostaerpserver.vo.FoodMaterial;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class FoodMaterialController {

    private final FoodMaterialService foodMaterialService;

    @GetMapping("/foodmaterials")
    public String getFoodMaterials(
            @RequestParam(defaultValue = "0000000000") String bId,
            @RequestParam(defaultValue = "idDesc") String sort,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "5") int size,
            Model model
    ) {

        List<FoodMaterial> foodList =
                foodMaterialService.getFoodMaterialList(bId, sort, page, size);

        int totalCount = foodMaterialService.getFoodMaterialCount(bId);
        int totalPage = (int) Math.ceil((double) totalCount / size);

        if (totalPage < 1) {
            totalPage = 1;
        }

        model.addAttribute("foodList", foodList);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPage", totalPage);
        model.addAttribute("sort", sort);
        model.addAttribute("size", size);
        model.addAttribute("bId", bId);

        return "foodMaterials";
    }
}