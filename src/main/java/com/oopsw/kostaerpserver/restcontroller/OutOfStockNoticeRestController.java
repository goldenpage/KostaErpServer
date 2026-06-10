package com.oopsw.kostaerpserver.restcontroller;

import com.oopsw.kostaerpserver.auth.ErpUserDetails;
import com.oopsw.kostaerpserver.dto.outofstock.OutOfStockNoticeResponse;
import com.oopsw.kostaerpserver.service.entity.OutOfStockNoticeServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/out-of-stock-notice")
public class OutOfStockNoticeRestController {
    private final OutOfStockNoticeServiceImpl outOfStockNoticeServiceImpl;

    @GetMapping
    public ResponseEntity<List<OutOfStockNoticeResponse>> getUnreadList(
            @AuthenticationPrincipal ErpUserDetails erpUserDetails) {
        String bId = erpUserDetails.getLoginUser().getBId();
        return ResponseEntity.ok(outOfStockNoticeServiceImpl.getUnreadList(bId));
    }

    @GetMapping("/count")
    public ResponseEntity<Integer> getUnreadCount(
            @AuthenticationPrincipal ErpUserDetails erpUserDetails
    ) {
        String bId = erpUserDetails.getLoginUser().getBId();
        return ResponseEntity.ok(outOfStockNoticeServiceImpl.getUnreadCount(bId));
    }

    @PatchMapping("/{noticeId}/read")
    public ResponseEntity<Void> markAsRead(@PathVariable int noticeId) {
        boolean success = outOfStockNoticeServiceImpl.markAsRead(noticeId);
        return success ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
    }
}
