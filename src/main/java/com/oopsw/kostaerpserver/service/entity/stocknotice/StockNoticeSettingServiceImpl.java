package com.oopsw.kostaerpserver.service.entity.stocknotice;

import com.oopsw.kostaerpserver.dto.stocknotice.StockNoticeRequest;
import com.oopsw.kostaerpserver.dto.stocknotice.StockNoticeResponse;
import com.oopsw.kostaerpserver.repository.entity.stocknotice.StockNoticeSetting;
import com.oopsw.kostaerpserver.repository.entity.stocknotice.StockNoticeSettingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class StockNoticeSettingServiceImpl implements StockNoticeSettingService {
    private final StockNoticeSettingRepository stockNoticeSettingRepository;

    @Override
    @Transactional
    public StockNoticeResponse getStockNoticeSetting(String bId) {
        StockNoticeSetting setting = getOrCreateStockNoticeSetting(bId);

        return new StockNoticeResponse(setting);
    }

    @Override
    @Transactional
    public StockNoticeResponse updateStockNoticeSetting(
            String bId,
            StockNoticeRequest request
    ) {
        StockNoticeSetting setting = getOrCreateStockNoticeSetting(bId);

        setting.update(
                request.isFoodmAlert(),
                request.getFoodmLimit()
        );

        return new StockNoticeResponse(setting);
    }

    private StockNoticeSetting getOrCreateStockNoticeSetting(String bId) {
        if (bId == null || bId.isBlank()) {
            throw new RuntimeException("사업자 번호가 없습니다.");
        }

        return stockNoticeSettingRepository.findByBId(bId)
                .orElseGet(() -> stockNoticeSettingRepository.save(
                        StockNoticeSetting.builder()
                                .bId(bId)
                                .foodmAlert(true)
                                .foodmLimit(5)
                                .build()
                ));
    }
}