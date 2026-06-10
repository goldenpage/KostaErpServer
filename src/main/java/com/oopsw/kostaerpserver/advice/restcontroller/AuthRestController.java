package com.oopsw.kostaerpserver.advice.restcontroller;


import com.oopsw.kostaerpserver.auth.ErpUserDetails;
import com.oopsw.kostaerpserver.dto.auth.ApiResponse;
import com.oopsw.kostaerpserver.dto.auth.LoginRequest;
import com.oopsw.kostaerpserver.dto.auth.RegisterRequest;
import com.oopsw.kostaerpserver.dto.auth.UserResponse;
import com.oopsw.kostaerpserver.service.Interface.LoginService;
import jakarta.servlet.http.HttpServletRequest;

import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.BadRequestException;
import org.springframework.http.ResponseEntity;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController()
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthRestController {

    private final LoginService loginService;
    private final AuthenticationManager authenticationManager;


    @PostMapping("/register")
    public ResponseEntity<Map> register(
        @RequestBody RegisterRequest registerRequest) {

        loginService.register(registerRequest);
        return ResponseEntity.ok().build();
    }


    @PostMapping("/phone/code")
    public ResponseEntity<ApiResponse> getPhoneCheck(
        @RequestBody RegisterRequest registerRequest) {

        loginService.getPhoneCheck(registerRequest.getPhone());
        return ResponseEntity.ok(new ApiResponse(true, "인증번호가 발송됐습니다."));
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
