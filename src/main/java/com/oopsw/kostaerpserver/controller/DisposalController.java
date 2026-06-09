package com.oopsw.kostaerpserver.controller;

import java.util.List;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.oopsw.kostaerpserver.service.Interface.DisposalService;
import com.oopsw.kostaerpserver.vo.Disposal;
import com.oopsw.kostaerpserver.dto.DisposalListResponse;


@Controller
@RequiredArgsConstructor
public class DisposalController {

    private final DisposalService disposalService;

    @GetMapping("/disposal-items")
    public String disposalItemsPage(
            @RequestParam(defaultValue = "0000000000") String bId,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String reason,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "5") int size,
            Model model){
        int totalCount = disposalService.getTotalCount(bId);
        int totalPages = (int) Math.ceil((double) totalCount / size);
        if (totalPages < 1) totalPages = 1;

        model.addAttribute("bId", bId);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("categories", disposalService.getCategories(bId));
        model.addAttribute("reasons", disposalService.getReasons());

        return "disposalItems";
    }
}