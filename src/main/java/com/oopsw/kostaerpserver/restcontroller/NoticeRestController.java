package com.oopsw.kostaerpserver.restcontroller;

import com.oopsw.kostaerpserver.dto.NoticeExpiredCountResponse;
import com.oopsw.kostaerpserver.dto.NoticeReadUpdateResponse;
import com.oopsw.kostaerpserver.dto.NoticeSearchRequest;
import com.oopsw.kostaerpserver.dto.NoticeListResponse;
import com.oopsw.kostaerpserver.dto.NoticeSolidTotalResponse;
import com.oopsw.kostaerpserver.dto.NoticeLiquidTotal;
import com.oopsw.kostaerpserver.dto.NoticeMaxOverDayResponse;
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
    public List<NoticeListResponse> getNoticeList(
            NoticeSearchRequest request
    ) {
        return noticeService.getNoticeList(request.getBId());
    }

    @GetMapping("/expired-count")
    public NoticeExpiredCountResponse getExpiredCount(
            NoticeSearchRequest request
    ) {
        int expiredCount = noticeService.getExpiredCount(request.getBId());
        return new NoticeExpiredCountResponse(expiredCount);
    }

    @GetMapping("/solid-total")
    public NoticeSolidTotalResponse getSolidTotal(NoticeSearchRequest request) {
        int solidTotal = noticeService.getSolidTotal(request.getBId());
        return new NoticeSolidTotalResponse(solidTotal);
    }

    @GetMapping("/liquid-total")
    public NoticeLiquidTotal getLiquidTotal(NoticeSearchRequest request) {
        int liquidTotal = noticeService.getLiquidTotal(request.getBId());
        return new NoticeLiquidTotal(liquidTotal);
    }

    @GetMapping("/max-over-day")
    public NoticeMaxOverDayResponse getMaxOverDay(NoticeSearchRequest request) {
        int maxOverDay = noticeService.getMaxOverDay(request.getBId());
        return new NoticeMaxOverDayResponse(maxOverDay);
    }

    @PatchMapping("/{noticeId}")
    public NoticeReadUpdateResponse updateReadYn(@PathVariable String noticeId) {
        boolean success = noticeService.updateReadYn(noticeId);
        return new NoticeReadUpdateResponse(success);
    }

}
