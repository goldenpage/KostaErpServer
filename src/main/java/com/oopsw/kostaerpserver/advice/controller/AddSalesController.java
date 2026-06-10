package com.oopsw.kostaerpserver.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AddSalesController {
    @GetMapping("/addSales")
    public String addSalePage() {
        return "addSales";
    }
}
