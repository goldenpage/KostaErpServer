package com.oopsw.kostaerpserver.dto.disposal;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DisposalSearchRequest {
    private String bId = "0000000000";
    private String category;
    private String type;
    private String reason;
    private int page = 1;
    private int size = 5;
}
