package com.oopsw.kostaerpserver.dto.notice;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class NoticeExpiredCountResponse {
    private int expiredCount;
}
