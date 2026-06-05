package com.oopsw.kostaerpserver.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(exclude = "pw")
public class RegisterRequest {
    @JsonProperty("bId")
    private String bId;

    @JsonProperty("pw")
    private String pw;
    private String phone;
    private String name;
    private String email;
    private String storeName;
    private String storeType;
    private String storeCategory;
    private LocalDateTime signDate;
    private LocalDateTime agreementDate;
    private boolean marketingAgree;
}
