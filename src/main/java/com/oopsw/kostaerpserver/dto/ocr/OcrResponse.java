package com.oopsw.kostaerpserver.dto.ocr;


import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OcrResponse {
    private String version;
    private String requestId;
    private Long timestamp;
    private List<OcrImageResult> images;
}
