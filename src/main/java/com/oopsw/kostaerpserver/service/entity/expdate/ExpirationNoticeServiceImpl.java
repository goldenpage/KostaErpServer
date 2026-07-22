package com.oopsw.kostaerpserver.service.entity.expdate;

import com.oopsw.kostaerpserver.dto.expnotice.ExpNoticeResponse;
import com.oopsw.kostaerpserver.dto.expnotice.ExpirationNoticeResponse;

import com.oopsw.kostaerpserver.repository.dao.FoodMaterialDAO;
import com.oopsw.kostaerpserver.service.entity.expdate.ExpNoticeService;
import com.oopsw.kostaerpserver.service.entity.expdate.ExpirationNoticeService;
import com.oopsw.kostaerpserver.vo.FoodMaterial;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ExpirationNoticeServiceImpl implements ExpirationNoticeService {

    private final FoodMaterialDAO foodMaterialDAO;
    private final ExpNoticeService expNoticeService;

    @Override
    public List<ExpirationNoticeResponse> getExpirationNoticeList(String bId) {
        ExpNoticeResponse setting = expNoticeService.getExpNotice(bId);

        if (!setting.isExpAlert()) {
            return List.of();
        }

        List<FoodMaterial> foodList =
                foodMaterialDAO.getExpirationNoticeList(bId, setting.getExpDays());

        return foodList.stream()
                .map(this::toResponse)
                .toList();
    }

    @Override
    public int getExpirationNoticeCount(String bId) {
        return getExpirationNoticeList(bId).size();
    }

    private ExpirationNoticeResponse toResponse(FoodMaterial food) {
        LocalDate expirationDate = convertToLocalDate(food.getExpirationDate());
        LocalDate today = LocalDate.now();

        long remainDays = ChronoUnit.DAYS.between(today, expirationDate);

        return ExpirationNoticeResponse.builder()
                .foodMaterialId(food.getFoodMaterialId())
                .foodMaterialName(food.getFoodMaterialName())
                .expirationDate(expirationDate.toString())
                .remainDays(remainDays)
                .noticeContent(makeNoticeContent(food.getFoodMaterialName(), remainDays))
                .build();
    }

    private LocalDate convertToLocalDate(Date date) {
        return date.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
    }

    private String makeNoticeContent(String foodMaterialName, long remainDays) {
        if (remainDays < 0) {
            return foodMaterialName + " 유통기한이 " + Math.abs(remainDays) + "일 지났습니다.";
        }

        if (remainDays == 0) {
            return foodMaterialName + " 유통기한이 오늘까지입니다.";
        }

        return foodMaterialName + " 유통기한이 " + remainDays + "일 남았습니다.";
    }
}