package com.oopsw.kostaerpserver.dto.ocr;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record PythonOcrResponse(
    String status,
    String businessRegistrationNumber,
    String companyName,
    String representativeName,
    String validityStartDate,
    String validityEndDate,
    OcrCandidate selectedCandidate,
    List<OcrCandidate> candidates
) {

    @JsonIgnoreProperties(ignoreUnknown = true)
    public record OcrCandidate(
        String number,
        String formattedNumber,
        Boolean checksumValid,
        Double score,
        Integer page,
        Integer blockIndex,
        Integer readingOrder,
        Double confidence,
        String sourceText,
        List<Double> bbox,
        List<String> corrections
    ) {

    }
}
