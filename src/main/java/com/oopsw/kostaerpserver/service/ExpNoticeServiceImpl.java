package com.oopsw.kostaerpserver.service;

import com.oopsw.kostaerpserver.dto.expnotice.ExpNoticeRequest;
import com.oopsw.kostaerpserver.dto.expnotice.ExpNoticeResponse;
import com.oopsw.kostaerpserver.repository.entity.expdate.ExpNotice;
import com.oopsw.kostaerpserver.repository.entity.expdate.ExpNoticeRepository;
import com.oopsw.kostaerpserver.service.Interface.ExpNoticeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ExpNoticeServiceImpl implements ExpNoticeService {
    private final ExpNoticeRepository expNoticeRepository;

    @Override
    @Transactional
    public ExpNoticeResponse getExpNotice(String bId) {
        ExpNotice expNotice = getOrCreateExpNotice(bId);

        return new ExpNoticeResponse(expNotice);
    }

    @Override
    @Transactional
    public ExpNoticeResponse updateExpNotice(String bId, ExpNoticeRequest request) {
        ExpNotice expNotice = getOrCreateExpNotice(bId);

        expNotice.update(
                request.isExpAlert(),
                request.getExpDays()
        );

        return new ExpNoticeResponse(expNotice);
    }

    private ExpNotice getOrCreateExpNotice(String bId) {
        if (bId == null || bId.isBlank()) {
            throw new RuntimeException("사업자 번호가 없습니다.");
        }

        return expNoticeRepository.findByBId(bId)
                .orElseGet(() -> expNoticeRepository.save(
                        ExpNotice.builder()
                                .bId(bId)
                                .expAlert(true)
                                .expDays(3)
                                .build()
                ));
    }
}