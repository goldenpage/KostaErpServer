package com.oopsw.kostaerpserver.dto.ocr;

import java.util.List;

public record DocumentReviewResult(
    String status,
    List<String> reasons
) {
}
