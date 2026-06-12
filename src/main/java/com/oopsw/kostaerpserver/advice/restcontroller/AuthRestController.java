package com.oopsw.kostaerpserver.advice.restcontroller;


import com.oopsw.kostaerpserver.auth.ErpUserDetails;
import com.oopsw.kostaerpserver.dto.auth.ApiResponse;
import com.oopsw.kostaerpserver.dto.auth.PhoneVerificationRequest;
import com.oopsw.kostaerpserver.dto.auth.RegisterRequest;
import com.oopsw.kostaerpserver.dto.auth.RegistrationResponse;
import com.oopsw.kostaerpserver.dto.auth.UserResponse;
import com.oopsw.kostaerpserver.service.Interface.RegistrationService;
import com.oopsw.kostaerpserver.service.PhoneVerificationService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RestController()
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthRestController {

    private final RegistrationService registrationService;
    private final PhoneVerificationService phoneVerificationService;


    @PostMapping(
        value = "/register",
        consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    public ResponseEntity<RegistrationResponse> register(
        @RequestPart("request") RegisterRequest request,
        @RequestPart("document") MultipartFile document,
        HttpSession session
    ) {
        request.setPhone(phoneVerificationService.requireVerified(request.getPhone(), session));
        RegistrationResponse response = registrationService.register(request, document);
        if ("APPROVED".equals(response.status()) || "PENDING".equals(response.status())) {
            phoneVerificationService.clear(session);
        }
        return ResponseEntity.ok(response);
    }


    @PostMapping("/phone/code")
    public ResponseEntity<ApiResponse> sendPhoneCode(
        @RequestBody PhoneVerificationRequest request,
        HttpSession session
    ) {
        phoneVerificationService.sendCode(request.phone(), session);
        return ResponseEntity.ok(new ApiResponse(
            true,
            "인증번호가 생성되었습니다. 현재 개발 환경에서는 서버 로그에서 인증번호를 확인해주세요."
        ));
    }

    @PostMapping("/phone/verify")
    public ResponseEntity<ApiResponse> verifyPhoneCode(
        @RequestBody PhoneVerificationRequest request,
        HttpSession session
    ) {
        phoneVerificationService.verify(request.phone(), request.code(), session);
        return ResponseEntity.ok(new ApiResponse(true, "휴대폰 인증이 완료되었습니다."));
    }


    @GetMapping("/userinfo")
    public ResponseEntity<UserResponse> getUser(@AuthenticationPrincipal
        ErpUserDetails erpUserDetails) {
        if (erpUserDetails == null) {
            return null;
        }
        return ResponseEntity.ok(new UserResponse(erpUserDetails.getLoginUser().getName()));
    }
}
