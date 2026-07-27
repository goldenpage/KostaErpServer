package com.oopsw.kostaerpserver.dto.ocr;

public record BusinessRegistrationValidationResult(
    boolean valid,
    String message
) {

}
