package com.oopsw.kostaerpserver.controller;

import java.time.LocalDate;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.oopsw.kostaerpserver.service.Interface.NoticeService;

@Controller
public class NoticeController {

    private final NoticeService noticeService;

    public NoticeController(NoticeService noticeService) {
        this.noticeService = noticeService;
    }

    @GetMapping("/notice")
    public String noticePage(
            @RequestParam(defaultValue = "0000000000") String bId,
            Model model) {

        model.addAttribute("bId", bId);
        model.addAttribute("list", noticeService.getNoticeList(bId));
        model.addAttribute("expiredCount", noticeService.getExpiredCount(bId));
        model.addAttribute("solidTotal", noticeService.getSolidTotal(bId));
        model.addAttribute("liquidTotal", noticeService.getLiquidTotal(bId));
        model.addAttribute("maxOverDay", noticeService.getMaxOverDay(bId));
        model.addAttribute("currentDate", LocalDate.now());

        return "notice";
    }
}
