package com.oopsw.kostaerpserver.advice.controller;

import com.oopsw.kostaerpserver.auth.ErpUserDetails;
import com.oopsw.kostaerpserver.dto.purchase.PurchaseResponse;
import com.oopsw.kostaerpserver.repository.entity.purchase.Purchase;
import com.oopsw.kostaerpserver.service.entity.purchase.PurchaseService;
import com.oopsw.kostaerpserver.vo.entity.PurchaseVO;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class PurchaseController {
    private final PurchaseService purchaseService;

    @GetMapping("/purchase")
    public String addPurchase(Model model,
    @AuthenticationPrincipal ErpUserDetails erpUserDetails)  {
        String bId = erpUserDetails.getLoginUser().getBId();

        List<Purchase> purchaseList = purchaseService.getPurchaseList(bId);
        model.addAttribute("purchaseList", PurchaseResponse.fromList(purchaseList));
        return "purchase";
    }
}
