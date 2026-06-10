package com.oopsw.kostaerpserver.service.entity;

import com.oopsw.kostaerpserver.dto.OutOfStockNoticeResponse;
import com.oopsw.kostaerpserver.repository.entity.outofstocknotice.OutOfStockNotice;
import com.oopsw.kostaerpserver.repository.entity.outofstocknotice.OutOfStockNoticeRepository;
import com.oopsw.kostaerpserver.vo.entity.OutOfStockNoticeVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OutOfStockNoticeServiceImpl {
    private final OutOfStockNoticeRepository outOfStockNoticeRepository;

    public boolean addOutOfStockNotice(OutOfStockNoticeVO vo) {
        OutOfStockNotice notice = outOfStockNoticeRepository.save(OutOfStockNotice.builder().
                noticeDate(LocalDateTime.parse(vo.getNoticeDate())).
                noticeContent(vo.getNoticeContent()).
                foodMaterialName(vo.getFoodMaterialName()).
                remainStockAmount(vo.getRemainStockAmount()).
                bId(vo.getBId()).
                build());

        return notice != null;
    }

    public List<OutOfStockNoticeResponse> getUnreadList(String bId){
        return outOfStockNoticeRepository.
                findByBIdAndReadYnOrderByNoticeDateDesc(bId,"N").
                stream().
                map(OutOfStockNoticeResponse::new).
                collect(Collectors.toList());
    }

    // 읽지 않은 알림 개수 (헤더 배지용)
    public int getUnreadCount(String bId){
        return 1;
    }

    // 단건 읽음 처리
    public boolean markAsRead(int noticeId){
        return false;
    }

    // 전체 읽음 처리
    public int markAllAsRead(String bId){
        return 1;
    }

}
