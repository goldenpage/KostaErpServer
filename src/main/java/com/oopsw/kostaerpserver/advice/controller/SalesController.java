package com.oopsw.kostaerpserver.advice.controller;

import com.oopsw.kostaerpserver.dto.salesrecord.SalesRecordResponse;
import com.oopsw.kostaerpserver.service.entity.salesrecord.SalesRecordService;
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
    public String salesList() {
        return "salesList";
    }
}