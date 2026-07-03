package com.oopsw.kostaerpserver.advice.restcontroller;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class testmain {
    public static void main(String[] args) {

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String rawPw = "test123";
        String encodedPw = encoder.encode(rawPw);
        System.out.println(encodedPw);

    }
}
