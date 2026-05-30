package com.oopsw.kostaerpserver.service;

import com.oopsw.kostaerpserver.repository.NoticeDAO;
import com.oopsw.kostaerpserver.vo.Notice;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class NoticeServiceImpl implements NoticeService {

    private final NoticeDAO noticeDAO;

    @Override
    @Transactional //추가 작업을 위해 쓰기 트랜잭션 적용
    public boolean insertNotice(String disposalId) {
        return noticeDAO.insertNotice(disposalId) == 1;
    }

    @Override
    public List<Notice> getNoticeList(String bId) {
        return noticeDAO.getNoticeList(bId);
    }

    @Override
    @Transactional //삭제 작업을 위해 쓰기 트랜잭션 적용
    public int deleteNoticeAll() {
        return noticeDAO.deleteNoticeAll();
    }

    @Override
    @Transactional //수정 작업을 위해 쓰기 트랜잭션 적용
    public boolean updateReadYn(String noticeId) {
        return noticeDAO.updateReadYn(noticeId) == 1;
    }

    @Override
    public List<String> getExpiredIdList(String bId) {
        return noticeDAO.getExpiredIdList(bId);
    }

    @Override
    public int getExpiredCount(String bId) {
        return noticeDAO.getExpiredCount(bId);
    }

    @Override
    public int getSolidTotal(String bId) {
        return noticeDAO.getSolidTotal(bId);
    }

    @Override
    public int getLiquidTotal(String bId) {
        return noticeDAO.getLiquidTotal(bId);
    }

    @Override
    public int getMaxOverDay(String bId) {
        return noticeDAO.getMaxOverDay(bId);
    }
}