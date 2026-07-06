package com.oopsw.kostaerpserver.advice.restcontroller;

import com.oopsw.kostaerpserver.auth.userdetails.AccountDetails;
import com.oopsw.kostaerpserver.dto.purchase.PurchaseResponse;
import com.oopsw.kostaerpserver.service.entity.purchase.PurchaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class PurchaseRestController {
    private final PurchaseService purchaseService;

    @GetMapping("/api/purchase")
    public List<PurchaseResponse> getPurchaseList(
            @AuthenticationPrincipal AccountDetails accountDetails) {
        String bId = accountDetails.getUsername();
        return PurchaseResponse.fromList(purchaseService.getPurchaseList(bId));
    }
}
