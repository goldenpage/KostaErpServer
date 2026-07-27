package com.oopsw.kostaerpserver.service.entity.expdate;

import com.oopsw.kostaerpserver.dto.expnotice.ExpNoticeRequest;
import com.oopsw.kostaerpserver.dto.expnotice.ExpNoticeResponse;

public interface ExpNoticeService {
    ExpNoticeResponse getExpNotice(String bId);
    ExpNoticeResponse updateExpNotice(String bId, ExpNoticeRequest request);
}