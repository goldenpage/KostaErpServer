package com.oopsw.kostaerpserver.advice.controller;

import java.time.YearMonth;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class StatisticsController {

    @GetMapping("/disposalstatistics")
    public String disposalstatistics(
        @RequestParam(required = false) String month,
        Model model
    ) {
        String selectedMonth = month == null || month.isBlank()
            ? YearMonth.now().toString()
            : month;
        model.addAttribute("selectedMonth", selectedMonth);
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
