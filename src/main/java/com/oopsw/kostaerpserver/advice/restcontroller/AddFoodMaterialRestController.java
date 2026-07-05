package com.oopsw.kostaerpserver.advice.restcontroller;

import com.oopsw.kostaerpserver.auth.userdetails.AccountDetails;
import com.oopsw.kostaerpserver.dto.addfoodmaterial.*;
import com.oopsw.kostaerpserver.dto.statistics.StatisticsRequest;
import com.oopsw.kostaerpserver.service.Interface.AddFoodMaterialService;
import com.oopsw.kostaerpserver.vo.AddFoodMaterial;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Slf4j
public class AddFoodMaterialRestController {
    private final AddFoodMaterialService addFoodMaterialService;

    @PostMapping("/foodmaterial/foodcategory/add")
    public AddFoodCategoryResponse  addFoodCategory(@RequestBody AddFoodCategoryRequest request) {
        try {
            int result = addFoodMaterialService.checkFoodCategoryExists(request.getFoodCategory());
            if (result > 0) {
                return AddFoodCategoryResponse.fail("이미 존재하는 카테고리");
            }

            addFoodMaterialService.addFoodCategory(request.toVO());
            String categoryId = addFoodMaterialService.getCategoryId(request.getFoodCategory());

            return AddFoodCategoryResponse.success(categoryId, request.getFoodCategory());

        } catch (Exception e) {
            return AddFoodCategoryResponse.fail("카테고리 추가 실패");
        }
    }

    @DeleteMapping("/foodmaterial/foodcategory/delete")
    public DeleteFoodCategoryResponse  deleteFoodCategory(@RequestBody DeleteFoodCategoryRequest request) {
        try{
            int exist = addFoodMaterialService.hasFoodMaterialByCategory(request.getFoodCategory());
            if (exist > 0) {
                return DeleteFoodCategoryResponse.fail("해당 카테고리가 사용중입니다.");
            }

            addFoodMaterialService.deleteFoodCategory(request.getFoodCategory());
            return DeleteFoodCategoryResponse.success(request.getFoodCategory());

        }catch (Exception e){
            return DeleteFoodCategoryResponse.fail("카테고리 삭제 오류");
        }
    }

    @GetMapping("/foodmaterial/search/add/{foodMaterialName}")
    public List<SearchFoodMaterialResponse> searchFoodMaterial(
            @PathVariable String foodMaterialName,
            @ModelAttribute StatisticsRequest statisticsRequest,
            @AuthenticationPrincipal AccountDetails accountDetails){
        String bId = accountDetails.getAccount().getUsername();
        return SearchFoodMaterialResponse.fromList(addFoodMaterialService.getFoodMaterialByName(foodMaterialName, bId));
    }

    @PostMapping("/foodmaterial/add")
    public AddFoodMaterialResponse addFoodMaterial(
            AddFoodMaterialRequest request,
            @ModelAttribute StatisticsRequest statisticsRequest,
            @AuthenticationPrincipal AccountDetails accountDetails){

        String bId = accountDetails.getAccount().getUsername();

        try{
            List<AddFoodMaterial> list = request.VOList(bId);
            for(AddFoodMaterial vo : list) {
                addFoodMaterialService.addFoodMaterial(vo);
            }
            return AddFoodMaterialResponse.success(list.size());

        } catch (Exception e){
            log.error("식자재 등록 실패", e);
            return AddFoodMaterialResponse.fail("식자재 등록 실패");
        }
    }
}
