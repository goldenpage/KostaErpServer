package com.oopsw.kostaerpserver.advice.restcontroller;

import com.oopsw.kostaerpserver.auth.ErpUserDetails;
import com.oopsw.kostaerpserver.dto.OutOfStockNoticeResponse;
import com.oopsw.kostaerpserver.service.entity.OutOfStockNoticeServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/outofstock-notice")
public class OutOfStockNoticeRestController {
    private final OutOfStockNoticeServiceImpl outOfStockNoticeServiceImpl;

    // 읽지 않은 알림 목록 (드롭박스 열 때 호출)
    @GetMapping
    public ResponseEntity<List<OutOfStockNoticeResponse>> getUnreadList(
            @AuthenticationPrincipal ErpUserDetails userDetails
    ) {
        String bId = getBId(userDetails);
        return ResponseEntity.ok(outOfStockNoticeServiceImpl.getUnreadList(bId));
    }

    // 읽지 않은 알림 개수 (헤더 배지 — 페이지 로드마다 호출)
    @GetMapping("/count")
    public ResponseEntity<Integer> getUnreadCount(
            @AuthenticationPrincipal ErpUserDetails userDetails
    ) {
        String bId = getBId(userDetails);
        return ResponseEntity.ok(outOfStockNoticeServiceImpl.getUnreadCount(bId));
    }

    // 단건 읽음 처리
    @PatchMapping("/{noticeId}/read")
    public ResponseEntity<Void> markAsRead(@PathVariable int noticeId) {
        boolean success = outOfStockNoticeServiceImpl.markAsRead(noticeId);
        return success ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
    }

    // 전체 읽음 처리
    @PatchMapping("/read-all")
    public ResponseEntity<Integer> markAllAsRead(
            @AuthenticationPrincipal ErpUserDetails userDetails
    ) {
        String bId = getBId(userDetails);
        return ResponseEntity.ok(outOfStockNoticeServiceImpl.markAllAsRead(bId));
    }

    private String getBId(ErpUserDetails userDetails) {
        if (userDetails == null) {
            throw new IllegalStateException("로그인 정보가 없습니다.");
        }
        return userDetails.getUsername();
    }
}
