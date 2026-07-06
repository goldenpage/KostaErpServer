package com.oopsw.kostaerpserver.advice.restcontroller;

import com.oopsw.kostaerpserver.auth.userdetails.AccountDetails;
import com.oopsw.kostaerpserver.dto.foodmaterial.FoodMaterialDeleteResponse;
import com.oopsw.kostaerpserver.dto.foodmaterial.FoodMaterialPageResponse;
import com.oopsw.kostaerpserver.dto.foodmaterial.FoodMaterialSearchRequest;
import com.oopsw.kostaerpserver.service.Interface.FoodMaterialService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/foodmaterials")
public class FoodMaterialRestController {

    private final FoodMaterialService foodMaterialService;

    @GetMapping
    public ResponseEntity<FoodMaterialPageResponse> getFoodMaterials(
            @ModelAttribute FoodMaterialSearchRequest request,
            @AuthenticationPrincipal AccountDetails accountDetails
    ) {
        String bId = getBId(accountDetails);
        request.setBId(bId);
        FoodMaterialPageResponse response = foodMaterialService.getFoodMaterialPage(request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{foodMaterialId}")
    public ResponseEntity<FoodMaterialDeleteResponse> deleteFoodMaterial(
            @PathVariable String foodMaterialId,
            @AuthenticationPrincipal AccountDetails accountDetails
    ) {
        String bId = getBId(accountDetails);
        foodMaterialService.deleteFoodMaterial(foodMaterialId, bId);

        return ResponseEntity.ok(new FoodMaterialDeleteResponse("삭제 완료", foodMaterialId)
        );
    }

    private String getBId(AccountDetails accountDetails) {
        if (accountDetails == null) {
            throw new RuntimeException("로그인 정보가 없습니다.");
        }
        return accountDetails.getUsername();
    }
}