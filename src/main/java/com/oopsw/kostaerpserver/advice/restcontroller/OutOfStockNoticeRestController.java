package com.oopsw.kostaerpserver.advice.restcontroller;

import com.oopsw.kostaerpserver.auth.userdetails.AccountDetails;
import com.oopsw.kostaerpserver.dto.outofstock.OutOfStockNoticeResponse;
import com.oopsw.kostaerpserver.dto.stocknotice.StockNoticeResponse;
import com.oopsw.kostaerpserver.service.entity.stocknotice.StockNoticeSettingService;
import com.oopsw.kostaerpserver.service.entity.outofstocknotice.OutOfStockNoticeServiceImpl;
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
    private final StockNoticeSettingService stockNoticeSettingService;

    @GetMapping
    public ResponseEntity<List<OutOfStockNoticeResponse>> getUnreadList(
            @AuthenticationPrincipal AccountDetails accountDetails) {
        String bId = getBId(accountDetails);
        StockNoticeResponse setting = stockNoticeSettingService.getStockNoticeSetting(bId);
        if (!setting.isFoodmAlert()) {
            return ResponseEntity.ok(List.of());
        }

        return ResponseEntity.ok(outOfStockNoticeServiceImpl.getUnreadList(bId));
    }

    @GetMapping("/count")
    public ResponseEntity<Integer> getUnreadCount(
            @AuthenticationPrincipal AccountDetails accountDetails
    ) {
        String bId = getBId(accountDetails);
        StockNoticeResponse setting = stockNoticeSettingService.getStockNoticeSetting(bId);

        if (!setting.isFoodmAlert()) {
            return ResponseEntity.ok(0);
        }
        return ResponseEntity.ok(outOfStockNoticeServiceImpl.getUnreadCount(bId));
    }

    @PatchMapping("/{noticeId}/read")
    public ResponseEntity<Void> markAsRead(@PathVariable int noticeId) {
        boolean success = outOfStockNoticeServiceImpl.markAsRead(noticeId);
        return success ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
    }

    @PatchMapping("/read-all")
    public ResponseEntity<Void> markAllAsRead(
            @AuthenticationPrincipal AccountDetails accountDetails) {
        outOfStockNoticeServiceImpl.markAllAsRead(getBId(accountDetails));
        return ResponseEntity.ok().build();
    }

    private String getBId(AccountDetails accountDetails) {
        if (accountDetails == null) {
            throw new IllegalStateException("로그인 정보가 없습니다.");
        }

        return accountDetails.getUsername();
    }
}