package com.oopsw.kostaerpserver.dto;

import lombok.Data;

@Data
public class DisposalCategoryRequest {
    private String category;
    private String bId = "0000000000";
}
