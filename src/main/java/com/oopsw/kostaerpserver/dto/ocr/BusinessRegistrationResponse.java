package com.oopsw.kostaerpserver.dto.ocr;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record BusinessRegistrationResponse(
    @JsonProperty("status_code") String statusCode,
    @JsonProperty("request_cnt") Integer requestCount,
    @JsonProperty("valid_cnt") Integer validCount,
    List<ValidationData> data
) {

    private static final String SUCCESS_STATUS_CODE = "OK";
    private static final String REGISTERED_CODE = "01";
    private static final String NOT_REGISTERED_CODE = "02";

    public BusinessRegistrationValidationResult validationResult(String businessNumber) {
        if (!SUCCESS_STATUS_CODE.equals(statusCode)) {
            throw new IllegalStateException("사업자등록번호 검증 서버가 실패 응답을 반환했습니다.");
        }

        ValidationData result = findResult(businessNumber);
        String validCode = result.valid();
        if (validCode == null) {
            throw new IllegalStateException("사업자등록번호 검증 코드가 없습니다.");
        }

        return switch (validCode) {
            case REGISTERED_CODE -> new BusinessRegistrationValidationResult(true, "");
            case NOT_REGISTERED_CODE -> new BusinessRegistrationValidationResult(
                false,
                rejectionMessage(result.validMessage())
            );
            default -> throw new IllegalStateException(
                "알 수 없는 사업자등록번호 검증 코드입니다: " + validCode
            );
        };
    }

    private String rejectionMessage(String validMessage) {
        if (validMessage == null || validMessage.isBlank()) {
            return "사업자등록정보가 일치하지 않습니다.";
        }
        return validMessage;
    }

    private ValidationData findResult(String businessNumber) {
        if (data == null || data.isEmpty()) {
            throw new IllegalStateException("사업자등록번호 검증 결과가 없습니다.");
        }

        return data.stream()
            .filter(result -> businessNumber.equals(result.businessNumber()))
            .findFirst()
            .orElseThrow(() ->
                new IllegalStateException("요청한 사업자등록번호의 검증 결과가 없습니다.")
            );
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public record ValidationData(
        @JsonProperty("b_no") String businessNumber,
        String valid,
        @JsonProperty("valid_msg") String validMessage
    ) {

    }
}
