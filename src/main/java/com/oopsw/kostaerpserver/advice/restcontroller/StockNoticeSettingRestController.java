package com.oopsw.kostaerpserver.advice.restcontroller;

import com.oopsw.kostaerpserver.auth.ErpUserDetails;
import com.oopsw.kostaerpserver.dto.stocknotice.StockNoticeRequest;
import com.oopsw.kostaerpserver.dto.stocknotice.StockNoticeResponse;
import com.oopsw.kostaerpserver.service.Interface.StockNoticeSettingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/notice/stock")
@RequiredArgsConstructor
public class StockNoticeSettingRestController {
    private final StockNoticeSettingService stockNoticeSettingService;

    @GetMapping
    public ResponseEntity<StockNoticeResponse> getStockNoticeSetting(
            @AuthenticationPrincipal ErpUserDetails userDetails
    ) {
        String bId = getBId(userDetails);

        StockNoticeResponse response =
                stockNoticeSettingService.getStockNoticeSetting(bId);

        return ResponseEntity.ok(response);
    }

    @PatchMapping
    public ResponseEntity<StockNoticeResponse> updateStockNoticeSetting(
            @RequestBody StockNoticeRequest request,
            @AuthenticationPrincipal ErpUserDetails userDetails
    ) {
        String bId = getBId(userDetails);

        StockNoticeResponse response =
                stockNoticeSettingService.updateStockNoticeSetting(bId, request);

        return ResponseEntity.ok(response);
    }

    private String getBId(ErpUserDetails userDetails) {
        if (userDetails == null) {
            throw new RuntimeException("로그인 정보가 없습니다.");
        }

        return userDetails.getUsername();
    }
}