package com.oopsw.kostaerpserver.dto.ocr;

public record BusinessVerificationResult(
    Status status,
    String extractedBusinessNumber,
    String message
) {
    public enum Status {
        APPROVED,
        REJECTED,
        NEED_REVIEW,
        RETRY
    }

    public static BusinessVerificationResult approved(String number) {
        return new BusinessVerificationResult(
            Status.APPROVED, number, "사업자등록번호가 확인되었습니다."
        );
    }

    public static BusinessVerificationResult rejected(String number, String message) {
        return new BusinessVerificationResult(Status.REJECTED, number, message);
    }

    public static BusinessVerificationResult needReview(String number, String message) {
        return new BusinessVerificationResult(Status.NEED_REVIEW, number, message);
    }

    public static BusinessVerificationResult retry(String number, String message) {
        return new BusinessVerificationResult(Status.RETRY, number, message);
    }
}
