package com.oopsw.kostaerpserver.dto.ocr;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OcrField {
    private String valueType;
    private String inferText;
    private Double inferConfidence;
}
