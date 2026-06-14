package com.oopsw.kostaerpserver.service.entity.outofstocknotice;

import com.oopsw.kostaerpserver.dto.outofstock.OutOfStockNoticeResponse;
import com.oopsw.kostaerpserver.vo.entity.OutOfStockNoticeVO;

import java.util.List;

public interface OutOfStockNotice {

    boolean addOutOfStockNotice(OutOfStockNoticeVO vo);

    List<OutOfStockNoticeResponse> getUnreadList(String bId);

    int getUnreadCount(String bId);

    boolean markAsRead(int noticeId);

    void markAllAsRead(String bId);

    boolean checkTodayNoticeExists(String bId, String foodMaterialName);
}
