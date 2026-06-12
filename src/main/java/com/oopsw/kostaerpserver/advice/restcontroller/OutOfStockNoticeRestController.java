package com.oopsw.kostaerpserver.advice.restcontroller;

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
        String bId = getBId(erpUserDetails);
        return ResponseEntity.ok(outOfStockNoticeServiceImpl.getUnreadList(bId));
    }

    @GetMapping("/count")
    public ResponseEntity<Integer> getUnreadCount(
            @AuthenticationPrincipal ErpUserDetails erpUserDetails
    ) {
        String bId = getBId(erpUserDetails);
        return ResponseEntity.ok(outOfStockNoticeServiceImpl.getUnreadCount(bId));
    }

    @PatchMapping("/{noticeId}/read")
    public ResponseEntity<Void> markAsRead(@PathVariable int noticeId) {
        boolean success = outOfStockNoticeServiceImpl.markAsRead(noticeId);
        return success ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
    }

    @PatchMapping("/read-all")
    public ResponseEntity<Void> markAllAsRead(
            @AuthenticationPrincipal ErpUserDetails erpUserDetails) {
        outOfStockNoticeServiceImpl.markAllAsRead(getBId(erpUserDetails));
        return ResponseEntity.ok().build();
    }

    private String getBId(ErpUserDetails userDetails) {
        if (userDetails == null) {
            throw new IllegalStateException("로그인 정보가 없습니다.");
        }

        return userDetails.getUsername();
    }
}
