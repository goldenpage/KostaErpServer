package com.oopsw.kostaerpserver.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.oopsw.kostaerpserver.dto.ocr.DocumentReviewResult;
import com.oopsw.kostaerpserver.dto.ocr.OcrField;
import com.oopsw.kostaerpserver.dto.ocr.OcrImageResult;
import com.oopsw.kostaerpserver.dto.ocr.OcrResponse;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@Slf4j
@ActiveProfiles("test")
@SpringBootTest
class DocumentValidationServiceImplTest {

    private final DocumentValidationServiceImpl service = new DocumentValidationServiceImpl();

    @Test
    void approvesWhenBusinessNumberMatchesWithHighConfidence() {
        DocumentReviewResult result = service.validateBusinessLicense(
            "123-45-67890",
            "사업자등록증 등록번호 123-45-67890",
            ocrResponse(0.95)
        );

        assertEquals("APPROVED", result.status());
        assertTrue(result.reasons().isEmpty());
    }

    @Test
    void rejectsWhenConfidentBusinessNumberDoesNotMatch() {
        DocumentReviewResult result = service.validateBusinessLicense(
            "123-45-67890",
            "사업자등록증 등록번호 999-99-99999",
            ocrResponse(0.95)
        );

        assertEquals("REJECTED", result.status());
        assertTrue(result.reasons().stream().anyMatch(reason -> reason.contains("일치하지 않습니다")));
    }

    @Test
    void requestsReviewWhenOcrConfidenceIsLow() {
        DocumentReviewResult result = service.validateBusinessLicense(
            "123-45-67890",
            "사업자등록증 등록번호 123-45-67890",
            ocrResponse(0.5)
        );

        assertEquals("NEED_REVIEW", result.status());
        assertTrue(result.reasons().stream().anyMatch(reason -> reason.contains("신뢰도가 낮습니다")));
    }

    private OcrResponse ocrResponse(double confidence) {
        OcrField field = new OcrField();
        field.setInferText("사업자등록증");
        field.setInferConfidence(confidence);

        OcrImageResult image = new OcrImageResult();
        image.setFields(List.of(field));

        return new OcrResponse("V2", "test-request", 1L, List.of(image));
    }
}
