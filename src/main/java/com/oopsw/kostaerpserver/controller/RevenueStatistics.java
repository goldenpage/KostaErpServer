package com.oopsw.kostaerpserver.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RevenueStatistics {

    @GetMapping("/revenuestatistics")
    public String revenuestatistic() {
        return "revenueStatistics";
    }
}
