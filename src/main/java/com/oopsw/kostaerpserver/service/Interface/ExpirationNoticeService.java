package com.oopsw.kostaerpserver.service.Interface;

import com.oopsw.kostaerpserver.dto.expnotice.ExpirationNoticeResponse;

import java.util.List;

public interface ExpirationNoticeService {
    List<ExpirationNoticeResponse> getExpirationNoticeList(String bId);
    int getExpirationNoticeCount(String bId);
}