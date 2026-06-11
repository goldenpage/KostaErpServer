package com.oopsw.kostaerpserver.advice.restcontroller;

import com.oopsw.kostaerpserver.auth.ErpUserDetails;
import com.oopsw.kostaerpserver.dto.menu.MenuDetailResponse;
import com.oopsw.kostaerpserver.dto.menu.MenuListRequest;
import com.oopsw.kostaerpserver.dto.menu.MenuListResponse;
import com.oopsw.kostaerpserver.dto.menu.MenuMaterialListResponse;
import com.oopsw.kostaerpserver.dto.menu.SaleRequest;
import com.oopsw.kostaerpserver.dto.menu.SaleResponse;
import com.oopsw.kostaerpserver.service.Interface.MenuService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/menus")
public class MenuRestController {

    private final MenuService menuService;

    @GetMapping
    public ResponseEntity<MenuListResponse> getMenuList(
            @ModelAttribute MenuListRequest request,
            @AuthenticationPrincipal ErpUserDetails userDetails
    ) {
        String bId = getBId(userDetails);
        request.setBId(bId);

        MenuListResponse response =
                menuService.getMenuListResponse(request);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{menuId}")
    public ResponseEntity<MenuDetailResponse> getMenu(
            @PathVariable String menuId
    ) {
        MenuDetailResponse response =
                menuService.getMenuDetailResponse(menuId);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{menuId}/materials")
    public ResponseEntity<MenuMaterialListResponse> getMenuMaterials(
            @PathVariable String menuId
    ) {
        MenuMaterialListResponse response =
                menuService.getMenuMaterialListResponse(menuId);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/{menuId}/sales")
    public ResponseEntity<SaleResponse> saleMenu(
            @PathVariable String menuId,
            @RequestBody SaleRequest request,
            @AuthenticationPrincipal ErpUserDetails userDetails
    ) {
        String bId = getBId(userDetails);
        menuService.saleMenu(menuId, request.getSaleCount(), bId);

        return ResponseEntity.ok(
                new SaleResponse("판매 처리 완료", menuId, request.getSaleCount())
        );
    }

    private String getBId(ErpUserDetails userDetails) {
        if (userDetails == null) {
            throw new IllegalStateException("로그인 정보가 없습니다.");
        }

        return userDetails.getUsername();
    }
}