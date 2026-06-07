package com.oopsw.kostaerpserver.restcontroller;

import com.oopsw.kostaerpserver.service.Interface.NoticeService;
import com.oopsw.kostaerpserver.vo.Notice;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/notice")
public class NoticeRestController {
    private final NoticeService noticeService;

    @GetMapping
    public List<Notice> getNoticeList(
            @RequestParam(defaultValue = "0000000000") String bId
    ) {
        return noticeService.getNoticeList(bId);
    }

    @GetMapping("/expired-count")
    public int getExpiredCount(
            @RequestParam(defaultValue = "0000000000") String bId
    ) {
        return noticeService.getExpiredCount(bId);
    }

    @GetMapping("/solid-total")
    public int getSolidTotal(
            @RequestParam(defaultValue = "0000000000") String bId
    ) {
        return noticeService.getSolidTotal(bId);
    }

    @GetMapping("/liquid-total")
    public int getLiquidTotal(
            @RequestParam(defaultValue = "0000000000") String bId
    ) {
        return noticeService.getLiquidTotal(bId);
    }

    @GetMapping("/max-over-day")
    public int getMaxOverDay(
            @RequestParam(defaultValue = "0000000000") String bId
    ) {
        return noticeService.getMaxOverDay(bId);
    }

    @PatchMapping("/{noticeId}")
    public boolean updateReadYn(@PathVariable String noticeId) {
        return noticeService.updateReadYn(noticeId);
    }

}
