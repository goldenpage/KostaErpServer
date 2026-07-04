package com.oopsw.kostaerpserver.auth.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class UserLoginRequest {
    private String username;
    private String password;
}
