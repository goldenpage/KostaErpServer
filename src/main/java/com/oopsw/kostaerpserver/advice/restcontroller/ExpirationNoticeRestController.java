package com.oopsw.kostaerpserver.advice.restcontroller;

import com.oopsw.kostaerpserver.auth.ErpUserDetails;
import com.oopsw.kostaerpserver.dto.expnotice.ExpirationNoticeResponse;
import com.oopsw.kostaerpserver.service.entity.expdate.ExpirationNoticeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/expiration-notice")
public class ExpirationNoticeRestController {
    private final ExpirationNoticeService expirationNoticeService;

    @GetMapping
    public ResponseEntity<List<ExpirationNoticeResponse>> getExpirationNoticeList(
            @AuthenticationPrincipal ErpUserDetails erpUserDetails
    ) {
        String bId = getBId(erpUserDetails);
        return ResponseEntity.ok(expirationNoticeService.getExpirationNoticeList(bId));
    }

    @GetMapping("/count")
    public ResponseEntity<Integer> getExpirationNoticeCount(
            @AuthenticationPrincipal ErpUserDetails erpUserDetails
    ) {
        String bId = getBId(erpUserDetails);
        return ResponseEntity.ok(expirationNoticeService.getExpirationNoticeCount(bId));
    }

    private String getBId(ErpUserDetails userDetails) {
        if (userDetails == null) {
            throw new IllegalStateException("로그인 정보가 없습니다.");
        }

        return userDetails.getUsername();
    }
}