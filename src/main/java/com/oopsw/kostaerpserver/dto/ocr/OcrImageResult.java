package com.oopsw.kostaerpserver.dto.ocr;

import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OcrImageResult {
    private String uid;
    private String name;
    private String inferResult;
    private String message;
    private ValidationResult validationResult;
    private List<OcrField> fields;
}
