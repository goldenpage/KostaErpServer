package com.oopsw.kostaerpserver.dto.ocr;

import java.util.List;

public record DocumentReviewResponse(
    String status,
    List<String> reasons,
    String extractedText
) {

    public boolean isApproved() {
        return "APPROVED".equals(status);
    }

    public boolean needsReview() {
        return "NEED_REVIEW".equals(status);
    }
}
