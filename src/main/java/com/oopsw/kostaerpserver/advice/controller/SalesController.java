package com.oopsw.kostaerpserver.advice.controller;

import com.oopsw.kostaerpserver.dto.salesrecord.SalesRecordResponse;
import com.oopsw.kostaerpserver.service.entity.SalesRecordService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class SalesController {
    private final SalesRecordService salesRecordService;

    @GetMapping("/sales-list")
    public String salesList(@RequestParam(value = "page", defaultValue = "1") int page,
                            @RequestParam(value = "size", defaultValue = "10") int size,
                            Model model) {

        Page<SalesRecordResponse> salesPage = salesRecordService.getSalesList(page, size);
        List<SalesRecordResponse> list = salesPage.getContent();


        int total = list.stream().mapToInt(SalesRecordResponse::getTotalPrice).sum();

        model.addAttribute("salesList", salesPage.getContent());
        model.addAttribute("totalSum", total);
        return "salesList";
    }
}