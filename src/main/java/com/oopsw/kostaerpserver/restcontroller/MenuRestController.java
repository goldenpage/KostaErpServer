package com.oopsw.kostaerpserver.restcontroller;

import com.oopsw.kostaerpserver.service.Interface.MenuService;
import com.oopsw.kostaerpserver.vo.Menu;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/menus")
public class MenuRestController {

    private final MenuService menuService;

    @GetMapping
    public List<Menu> getMenuList(
            @RequestParam(defaultValue = "0000000000") String bId
    ) {
        return menuService.getMenuList(bId);
    }

    @GetMapping("/{menuId}")
    public ResponseEntity<Menu> getMenu(
            @PathVariable String menuId
    ) {
        List<Menu> detailList = menuService.getMenuDetail(menuId);

        if (detailList.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(detailList.get(0));
    }

    @GetMapping("/{menuId}/materials")
    public List<Menu> getMenuMaterials(
            @PathVariable String menuId
    ) {
        return menuService.getMenuDetail(menuId);
    }

    @PostMapping("/{menuId}/sales")
    public ResponseEntity<String> saleMenu(
            @PathVariable String menuId,
            @RequestParam(defaultValue = "0000000000") String bId,
            @RequestBody SaleRequest request
    ) {
        menuService.saleMenu(menuId, request.getSaleCount(), bId);
        return ResponseEntity.ok("판매 처리 완료");
    }

    @Data
    static class SaleRequest {
        private int saleCount;
    }
}