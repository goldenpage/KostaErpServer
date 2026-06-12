package com.oopsw.kostaerpserver.service.client;

import com.oopsw.kostaerpserver.dto.ocr.PythonOcrResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Component
public class PythonOcrClient {

    private final RestClient restClient;

    public PythonOcrClient(
        @Qualifier("pythonOcrRestClient") RestClient restClient
    ) {
        this.restClient = restClient;
    }

    @Value("${external-api.python-ocr-url}")
    private String pythonOcrUrl;

    public PythonOcrResponse extractBusinessNumber(MultipartFile file) throws Exception {
        try {
            MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
            body.add("file", file.getResource());

            log.info(body.toString());

            return restClient.post()
                .uri(pythonOcrUrl)
                .body(body)
                .retrieve()
                .body(PythonOcrResponse.class);
        } catch (RestClientException exception) {
            log.warn(
                "Python OCR 요청에 실패했습니다. url={}, filename={}",
                pythonOcrUrl,
                file.getOriginalFilename(),
                exception
            );
            throw exception;
        }
    }
}
