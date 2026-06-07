package com.oopsw.kostaerpserver.controller;

import com.oopsw.kostaerpserver.service.Interface.LoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller()
@RequiredArgsConstructor
public class AuthController {

    private final LoginService loginService;

    @GetMapping("/login")
    public String login() {
        return "login";
    }


    @GetMapping("/register")
    public String register() {
        return "register";
    }

    @GetMapping("/pwupdate")
    public String pwUpdate() {
        return "pwUpdate";
    }
}
