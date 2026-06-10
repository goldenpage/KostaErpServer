package com.oopsw.kostaerpserver.service;

import com.oopsw.kostaerpserver.dto.ocr.DocumentReviewResponse;
import com.oopsw.kostaerpserver.dto.ocr.DocumentReviewResult;
import com.oopsw.kostaerpserver.dto.ocr.OcrField;
import com.oopsw.kostaerpserver.dto.ocr.OcrResponse;
import com.oopsw.kostaerpserver.service.Interface.DocumentReviewService;
import java.util.Objects;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


@Service
@RequiredArgsConstructor
public class DocumentReviewServiceImpl implements DocumentReviewService {

    private final OcrServiceImpl ocrService;
    private final DocumentValidationServiceImpl validationService;


    @Override
    public DocumentReviewResponse review(String expectedBid, MultipartFile multipartFile) {
        OcrResponse ocrResponse = ocrService.requestOcr(multipartFile);
        String fullText = extractFullText(ocrResponse);

        DocumentReviewResult reviewResult =
            validationService.validateBusinessLicense(expectedBid, fullText, ocrResponse);

        return new DocumentReviewResponse(
            reviewResult.status(),
            reviewResult.reasons(),
            fullText
        );
    }

    private String extractFullText(OcrResponse response) {

        if (response.getImages() == null) {
            return "";
        }

        return response.getImages().stream()
            .filter(image -> image.getFields() != null)
            .flatMap(image -> image.getFields().stream())
            .map(OcrField::getInferText)
            .filter(Objects::nonNull)
            .collect(Collectors.joining(" "));
    }
}
