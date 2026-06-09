package com.oopsw.kostaerpserver.restcontroller;

import com.oopsw.kostaerpserver.dto.foodmaterial.FoodMaterialDeleteResponse;
import com.oopsw.kostaerpserver.dto.foodmaterial.FoodMaterialPageResponse;
import com.oopsw.kostaerpserver.dto.foodmaterial.FoodMaterialSearchRequest;
import com.oopsw.kostaerpserver.service.Interface.FoodMaterialService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/foodmaterials")
public class FoodMaterialRestController {

    private final FoodMaterialService foodMaterialService;

    @GetMapping
    public ResponseEntity<FoodMaterialPageResponse> getFoodMaterials(
            @ModelAttribute FoodMaterialSearchRequest request
    ) {
        return ResponseEntity.ok(foodMaterialService.getFoodMaterialPage(request));
    }

    @DeleteMapping("/{foodMaterialId}")
    public ResponseEntity<FoodMaterialDeleteResponse> deleteFoodMaterial(
            @PathVariable String foodMaterialId,
            @RequestParam(defaultValue = "0000000000") String bId
    ) {
        foodMaterialService.deleteFoodMaterial(foodMaterialId, bId);

        return ResponseEntity.ok(
                new FoodMaterialDeleteResponse("삭제 완료", foodMaterialId)
        );
    }
}