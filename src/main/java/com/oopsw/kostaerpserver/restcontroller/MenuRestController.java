package com.oopsw.kostaerpserver.restcontroller;

import com.oopsw.kostaerpserver.dto.menu.MenuDetailResponse;
import com.oopsw.kostaerpserver.dto.menu.MenuListRequest;
import com.oopsw.kostaerpserver.dto.menu.MenuListResponse;
import com.oopsw.kostaerpserver.dto.menu.MenuMaterialListResponse;
import com.oopsw.kostaerpserver.dto.menu.SaleRequest;
import com.oopsw.kostaerpserver.dto.menu.SaleResponse;
import com.oopsw.kostaerpserver.service.Interface.MenuService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/menus")
public class MenuRestController {

    private final MenuService menuService;

    @GetMapping
    public ResponseEntity<MenuListResponse> getMenuList(
            @ModelAttribute MenuListRequest request
    ) {
        return ResponseEntity.ok(
                menuService.getMenuListResponse(request)
        );
    }

    @GetMapping("/{menuId}")
    public ResponseEntity<MenuDetailResponse> getMenu(
            @PathVariable String menuId
    ) {
        return ResponseEntity.ok(
                menuService.getMenuDetailResponse(menuId)
        );
    }

    @GetMapping("/{menuId}/materials")
    public ResponseEntity<MenuMaterialListResponse> getMenuMaterials(
            @PathVariable String menuId
    ) {
        return ResponseEntity.ok(
                menuService.getMenuMaterialListResponse(menuId)
        );
    }

    @PostMapping("/{menuId}/sales")
    public ResponseEntity<SaleResponse> saleMenu(
            @PathVariable String menuId,
            @RequestParam(defaultValue = "0000000000") String bId,
            @RequestBody SaleRequest request
    ) {
        menuService.saleMenu(menuId, request.getSaleCount(), bId);

        return ResponseEntity.ok(
                new SaleResponse("판매 처리 완료", menuId, request.getSaleCount())
        );
    }
}