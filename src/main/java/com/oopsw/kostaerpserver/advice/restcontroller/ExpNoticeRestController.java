package com.oopsw.kostaerpserver.advice.restcontroller;

import com.oopsw.kostaerpserver.auth.userdetails.AccountDetails;
import com.oopsw.kostaerpserver.dto.expnotice.ExpNoticeRequest;
import com.oopsw.kostaerpserver.dto.expnotice.ExpNoticeResponse;
import com.oopsw.kostaerpserver.service.entity.expdate.ExpNoticeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/notice/exp")
@RequiredArgsConstructor
public class ExpNoticeRestController {

    private final ExpNoticeService expNoticeService;

    @GetMapping
    public ResponseEntity<ExpNoticeResponse> getExpNotice(
            @AuthenticationPrincipal AccountDetails accountDetails
            ) {
        String bId = getBId(accountDetails);

        ExpNoticeResponse response = expNoticeService.getExpNotice(bId);

        return ResponseEntity.ok(response);
    }

    @PatchMapping
    public ResponseEntity<ExpNoticeResponse> updateExpNotice(
            @RequestBody ExpNoticeRequest request,
            @AuthenticationPrincipal AccountDetails accountDetails
    ) {
        String bId = getBId(accountDetails);

        ExpNoticeResponse response =
                expNoticeService.updateExpNotice(bId, request);

        return ResponseEntity.ok(response);
    }

    private String getBId(AccountDetails accountDetails) {
        if (accountDetails == null) {
            throw new IllegalStateException("로그인 정보가 없습니다.");
        }

        return accountDetails.getUsername();
    }
}