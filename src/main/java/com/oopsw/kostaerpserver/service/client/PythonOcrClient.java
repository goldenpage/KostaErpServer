package com.oopsw.kostaerpserver.service.client;

import com.oopsw.kostaerpserver.dto.ocr.PythonOcrResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClient;
import org.springframework.web.multipart.MultipartFile;

@Component
@RequiredArgsConstructor
public class PythonOcrClient {

    private final RestClient.Builder restClientBuilder;

    @Value("${external-api.python-ocr-url}")
    private String pythonOcrUrl;

    public PythonOcrResponse extractBusinessNumber(MultipartFile file) throws Exception {
        ByteArrayResource resource = new ByteArrayResource(file.getBytes()) {
            @Override
            public String getFilename() {
                return file.getOriginalFilename();
            }
        };

        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("file", resource);

        return restClientBuilder.build()
            .post()
            .uri(pythonOcrUrl)
            .contentType(MediaType.MULTIPART_FORM_DATA)
            .body(body)
            .retrieve()
            .body(PythonOcrResponse.class);
    }
}