package com.oopsw.kostaerpserver.service.Interface;

import com.oopsw.kostaerpserver.dto.ocr.DocumentReviewResult;
import com.oopsw.kostaerpserver.dto.ocr.OcrResponse;

public interface DocumentValidationService {

    DocumentReviewResult validateBusinessLicense(
        String expectedBid,
        String text,
        OcrResponse ocrResponse
    );
}
