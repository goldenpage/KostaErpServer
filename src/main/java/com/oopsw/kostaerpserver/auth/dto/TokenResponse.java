package com.oopsw.kostaerpserver.auth.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class TokenResponse {
    private String accessToken;
    private String refreshToken;
}
