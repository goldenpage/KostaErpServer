package com.oopsw.kostaerpserver.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(exclude = "pw")
public class LoginRequest {

    @JsonProperty("bId")
    private String bId;

    @JsonProperty("pw")
    private String pw;
}
