package com.oopsw.kostaerpserver.service;

import com.oopsw.kostaerpserver.dto.ocr.DocumentReviewResult;
import com.oopsw.kostaerpserver.dto.ocr.OcrField;
import com.oopsw.kostaerpserver.dto.ocr.OcrResponse;
import com.oopsw.kostaerpserver.repository.UserInfoDAO;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DocumentValidationServiceImpl {
    

    public DocumentReviewResult validateBusinessLicense(
        String text,
        OcrResponse ocrResponse
    ) {
        List<String> reasons = new ArrayList<>();

        if (!text.contains("사업자등록증")) {
            reasons.add("사업자등록증 문구를 찾을 수 없습니다.");
        }

        if (!hasBusinessNumber(text)) {
            reasons.add("사업자등록번호를 찾을 수 없습니다.");
        }

        double avgConfidence = getAverageConfidence(ocrResponse);

        if (avgConfidence < 0.85) {
            reasons.add("OCR 평균 신뢰도가 낮습니다.");
        }

        String status;


        if (reasons.isEmpty()) {
            status = "APPROVED";
        } else if (avgConfidence < 0.7) {
            status = "NEED_REVIEW";
        } else {
            status = "REJECTED";
        }

        return new DocumentReviewResult(status, reasons);
    }

    private boolean hasBusinessNumber(String text) {
        return Pattern.compile("\\d{3}-\\d{2}-\\d{5}")
            .matcher(text)
            .find();
    }

    private double getAverageConfidence(OcrResponse response) {
        if (response.getImages() == null) {
            return 0.0;
        }

        return response.getImages().stream()
            .filter(image -> image.getFields() != null)
            .flatMap(image -> image.getFields().stream())
            .map(OcrField::getInferConfidence)
            .filter(Objects::nonNull)
            .mapToDouble(Double::doubleValue)
            .average()
            .orElse(0.0);
    }
}