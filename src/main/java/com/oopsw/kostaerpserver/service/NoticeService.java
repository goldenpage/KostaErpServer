package com.oopsw.kostaerpserver.service;

import com.oopsw.kostaerpserver.repository.NoticeDAO;
import com.oopsw.kostaerpserver.vo.Notice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class NoticeService {
    private final NoticeDAO noticeDAO;

    public NoticeService(NoticeDAO noticeDAO) {
        this.noticeDAO = noticeDAO;
    }

    @Transactional
    public boolean insertNotice(String disposalId) {
        return noticeDAO.insertNotice(disposalId) == 1;
    }

    public List<Notice> getNoticeList(String bId) {
        return noticeDAO.getNoticeList(bId);
    }

    @Transactional
    public int deleteNoticeAll() {
        return noticeDAO.deleteNoticeAll();
    }

    @Transactional
    public boolean updateReadYn(String noticeId) {
        return noticeDAO.updateReadYn(noticeId) == 1;
    }

    public List<String> getExpiredIdList(String bId) {
        return noticeDAO.getExpiredIdList(bId);
    }

    public int getExpiredCount(String bId) {
        return noticeDAO.getExpiredCount(bId);
    }

    public int getSolidTotal(String bId) {
        return noticeDAO.getSolidTotal(bId);
    }

    public int getLiquidTotal(String bId) {
        return noticeDAO.getLiquidTotal(bId);
    }

    public int getMaxOverDay(String bId) {
        return noticeDAO.getMaxOverDay(bId);
    }
}
