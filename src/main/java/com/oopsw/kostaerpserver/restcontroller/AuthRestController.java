package com.oopsw.kostaerpserver.restcontroller;


import com.oopsw.kostaerpserver.dto.ApiResponse;
import com.oopsw.kostaerpserver.dto.LoginRequest;
import com.oopsw.kostaerpserver.dto.RegisterRequest;
import com.oopsw.kostaerpserver.service.Interface.LoginService;
import com.oopsw.kostaerpserver.vo.User;
import jakarta.servlet.http.HttpSession;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.BadRequestException;
import org.springframework.http.ResponseEntity;

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

    @PostMapping("/login")
    public ResponseEntity<Map> login(@RequestBody LoginRequest loginRequest,
        HttpSession session)
        throws BadRequestException {
        User user = loginService.login(loginRequest.getBId(),
            loginRequest.getPw());
        session.setAttribute("info", user);
        log.info("로그인처리");
        return ResponseEntity.ok().build();
    }

    @PostMapping("/users")
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
}
