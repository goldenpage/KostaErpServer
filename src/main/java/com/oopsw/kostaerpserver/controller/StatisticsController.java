package com.oopsw.kostaerpserver.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class StatisticsController {

    @GetMapping("/disposalstatistics")
    public String disposalstatistics() {
        return "disposalStatistics";
    }

    @GetMapping("/usedstatistics")
    public String usedstatistics() {
        return "usedStatistics";
    }

    @GetMapping("/revenuestatistics")
    public String revenuestatistic() {
        return "revenueStatistics";
    }


}
