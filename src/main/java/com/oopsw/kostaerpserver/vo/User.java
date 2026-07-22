package com.oopsw.kostaerpserver.vo;


import java.time.LocalDateTime;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {
    private String bId;
    private String pw;
    private String phone;
    private String name;
    private String email;
    private String storeName;
    private String storeType;
    private String storeCategory;
    private LocalDateTime signDate;
    private LocalDateTime agreementDate;
    private LocalDateTime marketingDate;
}
