package com.oopsw.kostaerpserver.advice.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SalesController {
    @GetMapping("/sales-list")
    public String salesList() {
        return "salesList";
    }
}
