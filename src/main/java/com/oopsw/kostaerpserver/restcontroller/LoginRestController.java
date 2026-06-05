package com.oopsw.kostaerpserver.restcontroller;


import com.oopsw.kostaerpserver.dto.LoginRequest;
import com.oopsw.kostaerpserver.dto.RegisterRequest;
import com.oopsw.kostaerpserver.service.Interface.LoginService;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@RequestMapping("/api")
@RequiredArgsConstructor
public class LoginRestController {

    private final LoginService loginService;


    @PostMapping("/auth/login")
    public ResponseEntity<Map> login(@RequestBody LoginRequest loginRequest)
        throws BadRequestException {
        loginService.login(loginRequest.getBId(), loginRequest.getPw());
        return ResponseEntity.ok().build();

    }

    @PostMapping("/auth/users")
    public ResponseEntity<Map> register(
        @RequestBody RegisterRequest registerRequest) {

        loginService.register(registerRequest);
        return ResponseEntity.ok().build();
    }
}
