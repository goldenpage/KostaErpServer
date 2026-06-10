package com.oopsw.kostaerpserver.service.Interface;

import com.oopsw.kostaerpserver.dto.notice.NoticeListResponse;

import java.util.List;

public interface NoticeService {
    boolean insertNotice(String disposalId);

    List<NoticeListResponse> getNoticeList(String bId);

    int deleteNoticeAll();

    boolean updateReadYn(String noticeId);

    List<String> getExpiredIdList(String bId);

    int getExpiredCount(String bId);

    int getSolidTotal(String bId);

    int getLiquidTotal(String bId);

    int getMaxOverDay(String bId);
}