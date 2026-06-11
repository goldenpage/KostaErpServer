package com.oopsw.kostaerpserver.service.entity;

import com.oopsw.kostaerpserver.dto.outofstock.OutOfStockNoticeResponse;
import com.oopsw.kostaerpserver.repository.entity.outofstocknotice.OutOfStockNotice;
import com.oopsw.kostaerpserver.repository.entity.outofstocknotice.OutOfStockNoticeRepository;
import com.oopsw.kostaerpserver.vo.entity.OutOfStockNoticeVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OutOfStockNoticeServiceImpl implements com.oopsw.kostaerpserver.service.entity.OutOfStockNotice {
    private final OutOfStockNoticeRepository outOfStockNoticeRepository;

    @Override
    @Transactional
    public boolean addOutOfStockNotice(OutOfStockNoticeVO vo) {
        OutOfStockNotice notice = outOfStockNoticeRepository.save(OutOfStockNotice.builder().
                noticeContent(vo.getNoticeContent()).
                foodMaterialName(vo.getFoodMaterialName()).
                remainStockAmount(vo.getRemainStockAmount()).
                bId(vo.getBId()).
                build());

        return notice != null;
    }

    @Override
    public List<OutOfStockNoticeResponse> getUnreadList(String bId){
        return outOfStockNoticeRepository.
                findByBIdAndReadYnOrderByNoticeDateDesc(bId,"N").
                stream().
                map(OutOfStockNoticeResponse::new).
                collect(Collectors.toList());
    }

    @Override
    public int getUnreadCount(String bId){
        return outOfStockNoticeRepository.countByBIdAndReadYn(bId, "N");
    }

    @Override
    @Transactional
    public boolean markAsRead(int noticeId){
        return outOfStockNoticeRepository.findById(noticeId).
                map(notice ->{
                    notice.markAsRead();
                    return true;
                }).
                orElse(false);
    }

    @Override
    @Transactional
    public void markAllAsRead(String bId) {
        outOfStockNoticeRepository
                .findByBIdAndReadYnOrderByNoticeDateDesc(bId, "N")
                .forEach(OutOfStockNotice::markAsRead);
    }

    @Override
    public boolean checkTodayNoticeExists(String bId, String foodMaterialName) {
        return outOfStockNoticeRepository.existsTodayNotice(bId, foodMaterialName);
    }
}
