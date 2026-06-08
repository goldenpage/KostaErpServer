package com.oopsw.kostaerpserver.dto;

import lombok.Data;

@Data
public class DisposalTypeRequest {
    private String type;
    private String bId = "0000000000";
}
