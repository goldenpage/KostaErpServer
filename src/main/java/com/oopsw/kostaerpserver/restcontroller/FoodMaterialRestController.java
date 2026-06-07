package com.oopsw.kostaerpserver.restcontroller;

import com.oopsw.kostaerpserver.service.Interface.FoodMaterialService;
import com.oopsw.kostaerpserver.vo.FoodMaterial;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/foodmaterials")
public class FoodMaterialRestController {

    private final FoodMaterialService foodMaterialService;

    @GetMapping
    public List<FoodMaterial> getFoodMaterials(
            @RequestParam(defaultValue = "0000000000") String bId,
            @RequestParam(defaultValue = "idDesc") String sort,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(required = false) String keyword
    ) {
        if (keyword != null && !keyword.isBlank()) {
            return foodMaterialService.searchFoodMaterial(bId, keyword);
        }

        return foodMaterialService.getFoodMaterialList(bId, sort, page, size);
    }

    @DeleteMapping("/{foodMaterialId}")
    public ResponseEntity<String> deleteFoodMaterial(
            @PathVariable String foodMaterialId,
            @RequestParam(defaultValue = "0000000000") String bId
    ) {
        foodMaterialService.deleteFoodMaterial(foodMaterialId, bId);

        return ResponseEntity.ok("삭제 완료");
    }
}