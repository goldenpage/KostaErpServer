package com.oopsw.kostaerpserver.service.entity;

import com.oopsw.kostaerpserver.vo.entity.OutOfStockNoticeVO;

import java.util.List;

public interface OutOfStockNotice {

    // 알림 저장
    boolean addOutOfStockNotice(OutOfStockNoticeVO vo);

    // 읽지 않은 알림 목록 조회
    List<OutOfStockNoticeResponse> getUnreadList(String bId);

    // 읽지 않은 알림 개수 (헤더 배지용)
    int getUnreadCount(String bId);

    // 단건 읽음 처리
    boolean markAsRead(int noticeId);

    // 전체 읽음 처리
    int markAllAsRead(String bId);
}
