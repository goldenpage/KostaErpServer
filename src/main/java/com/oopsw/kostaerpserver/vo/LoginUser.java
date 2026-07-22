package com.oopsw.kostaerpserver.vo;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginUser {
    private String bId;
    private String pw;
    private String name;

    private String role = "ROLE_USER";
}
