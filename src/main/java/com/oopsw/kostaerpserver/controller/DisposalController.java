package com.oopsw.kostaerpserver.controller;

import com.oopsw.kostaerpserver.auth.ErpUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.oopsw.kostaerpserver.service.Interface.DisposalService;


@Controller
@RequiredArgsConstructor
public class DisposalController {

    private final DisposalService disposalService;

    @GetMapping("/disposal-items")
    public String disposalItemsPage(@AuthenticationPrincipal ErpUserDetails erpUserDetails, Model model) {
        String bId = erpUserDetails.getLoginUser().getBId();

        model.addAttribute("bId", bId);
        model.addAttribute("categories", disposalService.getCategories(bId));
        model.addAttribute("reasons", disposalService.getReasons());

        return "disposalItems";
    }
}