package com.oopsw.kostaerpserver.controller;

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
        model.addAttribute("categoryList", categoryList);

        return "addFoodMaterial";
    }

    @PostMapping("/foodmaterial/add")
    public String addFoodMaterial(
            @RequestParam(value = "foodMaterialName") List<String>  foodMaterialName,
            @RequestParam(value = "foodCategory_Id") List<String>  foodCategory_Id,
            @RequestParam(value = "foodMaterialCount") List<String>  foodMaterialCount,
            @RequestParam(value = "foodMaterialCountAll") List<String>  foodMaterialCountAll,
            @RequestParam(value = "foodMaterialPrice") List<String>  foodMaterialPrice,
            @RequestParam(value = "foodMaterialType") List<String>  foodMaterialType,
            @RequestParam(value = "vender") List<String>  vender,
            @RequestParam(value = "incomeDate") List<String>  incomeDate,
            @RequestParam(value = "expirationDate") List<String>  expirationDate,
            HttpSession session,
            RedirectAttributes redirectAttributes){

        String bId = "0000000000";
                //(String) session.getAttribute("loginOK");

        try{
            List<AddFoodMaterial> list = new ArrayList<>();
            for(int i = 0; i < foodMaterialName.size(); i++){
                AddFoodMaterial vo = new AddFoodMaterial();
                vo.setFoodMaterialName(foodMaterialName.get(i));
                vo.setFoodCategory_Id(foodCategory_Id.get(i));
                vo.setFoodMaterialCount(Integer.parseInt(foodMaterialCount.get(i)));
                vo.setFoodMaterialCountAll(Integer.parseInt(foodMaterialCountAll.get(i)));
                vo.setFoodMaterialPrice(Integer.parseInt(foodMaterialPrice.get(i)));
                vo.setFoodMaterialType(foodMaterialType.get(i));
                vo.setVender(vender.get(i));
                vo.setIncomeDate(Date.valueOf(incomeDate.get(i)));
                vo.setExpirationDate(Date.valueOf(expirationDate.get(i)));
                vo.setBId(bId);
                list.add(vo);
            }
            System.out.println(list);
            for(AddFoodMaterial vo : list){
                addFoodMaterialService.addFoodMaterial(vo);
            }
            redirectAttributes.addFlashAttribute("successMessage", list.size() + "개의 식자재 등록 완료");

        } catch (Exception e){
            redirectAttributes.addFlashAttribute("errorMessage", "식자재 등록 실패");
        }

        return "redirect:/foodmaterial/add";
    }
}
