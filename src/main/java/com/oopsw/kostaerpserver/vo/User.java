package com.oopsw.kostaerpserver.vo;


import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
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
    private Date signDate;
    private Date agreementDate;
    private Date marketingDate;
}
