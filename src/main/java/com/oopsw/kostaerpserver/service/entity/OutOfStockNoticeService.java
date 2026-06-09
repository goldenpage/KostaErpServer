package com.oopsw.kostaerpserver.service.entity;

import com.oopsw.kostaerpserver.repository.entity.outofstocknotice.OutOfStockNotice;
import com.oopsw.kostaerpserver.repository.entity.outofstocknotice.OutOfStockNoticeRepository;
import com.oopsw.kostaerpserver.vo.entity.OutOfStockNoticeVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class OutOfStockNoticeService {
    private final OutOfStockNoticeRepository outOfStockNoticeRepository;

    public boolean addOutOfStockNotice(OutOfStockNoticeVO vo) {
        OutOfStockNotice notice = outOfStockNoticeRepository.save(OutOfStockNotice.builder().
                noticeDate(LocalDateTime.parse(vo.getNoticeDate())).
                noticeContent(vo.getNoticeContent()).
                foodMaterialName(vo.getFoodMaterialName()).
                remainStockAmount(vo.getRemainStockAmount()).
                build());

        return notice != null;
    }
}
