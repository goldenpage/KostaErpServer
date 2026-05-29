package com.oopsw.kostaerpserver.service;

import com.oopsw.kostaerpserver.vo.Notice;
import java.util.List;

public interface NoticeService {
    boolean insertNotice(String disposalId);

    List<Notice> getNoticeList(String bId);

    int deleteNoticeAll();

    boolean updateReadYn(String noticeId);

    List<String> getExpiredIdList(String bId);

    int getExpiredCount(String bId);

    int getSolidTotal(String bId);

    int getLiquidTotal(String bId);

    int getMaxOverDay(String bId);
}