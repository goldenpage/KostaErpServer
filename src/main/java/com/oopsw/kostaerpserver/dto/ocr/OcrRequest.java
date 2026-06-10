package com.oopsw.kostaerpserver.dto.ocr;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "clova.ocr")
public class OcrRequest {
    private String invokeUrl;
    private String secretKey;

}
