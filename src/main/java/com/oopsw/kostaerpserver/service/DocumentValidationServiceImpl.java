package com.oopsw.kostaerpserver.service;

import com.oopsw.kostaerpserver.dto.ocr.DocumentReviewResult;
import com.oopsw.kostaerpserver.dto.ocr.OcrField;
import com.oopsw.kostaerpserver.dto.ocr.OcrResponse;
import com.oopsw.kostaerpserver.service.Interface.DocumentValidationService;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.springframework.stereotype.Service;

@Service
public class DocumentValidationServiceImpl implements DocumentValidationService {

    private static final double AUTO_REVIEW_CONFIDENCE = 0.85;
    private static final Pattern BUSINESS_NUMBER_PATTERN = Pattern.compile(
        "(?<!\\d)(\\d{3})[\\s-]*(\\d{2})[\\s-]*(\\d{5})(?!\\d)"
    );

    @Override
    public DocumentReviewResult validateBusinessLicense(
        String expectedBid,
        String text,
        OcrResponse ocrResponse
    ) {
        String normalizedExpectedBid = normalizeBid(expectedBid);
        Set<String> extractedBids = extractBusinessNumbers(text);
        double avgConfidence = getAverageConfidence(ocrResponse);
        List<String> reasons = new ArrayList<>();

        if (!text.contains("사업자등록증")) {
            reasons.add("사업자등록증 문구를 찾을 수 없습니다.");
        }

        if (extractedBids.isEmpty()) {
            reasons.add("사업자등록번호를 찾을 수 없습니다.");
        } else if (!extractedBids.contains(normalizedExpectedBid)) {
            reasons.add("입력한 사업자등록번호와 서류의 사업자등록번호가 일치하지 않습니다.");
        }

        if (avgConfidence < AUTO_REVIEW_CONFIDENCE) {
            reasons.add("OCR 평균 신뢰도가 낮습니다.");
        }

        if (reasons.isEmpty()) {
            return new DocumentReviewResult("APPROVED", reasons);
        }

        boolean confidentMismatch =
            !extractedBids.isEmpty()
                && !extractedBids.contains(normalizedExpectedBid)
                && avgConfidence >= AUTO_REVIEW_CONFIDENCE;

        return new DocumentReviewResult(
            confidentMismatch ? "REJECTED" : "NEED_REVIEW",
            reasons
        );
    }

    private String normalizeBid(String bid) {
        if (bid == null || bid.isBlank()) {
            throw new IllegalArgumentException("사업자등록번호는 필수입니다.");
        }

        String normalized = bid.replaceAll("\\D", "");
        if (normalized.length() != 10) {
            throw new IllegalArgumentException("사업자등록번호 형식이 올바르지 않습니다.");
        }
        return normalized;
    }

    private Set<String> extractBusinessNumbers(String text) {
        Set<String> businessNumbers = new HashSet<>();
        Matcher matcher = BUSINESS_NUMBER_PATTERN.matcher(text == null ? "" : text);

        while (matcher.find()) {
            businessNumbers.add(matcher.group(1) + matcher.group(2) + matcher.group(3));
        }
        return businessNumbers;
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
