package com.oopsw.kostaerpserver.dto.expnotice;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ExpNoticeRequest {
    private boolean expAlert;
    private int expDays;
}