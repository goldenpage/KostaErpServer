package com.oopsw.kostaerpserver.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.oopsw.kostaerpserver.dto.ocr.OcrRequest;
import com.oopsw.kostaerpserver.dto.ocr.OcrResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClient;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class OcrServiceImpl {

    private final ObjectMapper objectMapper;
    private final RestClient.Builder restClientBuilder;
    private final OcrRequest ocrRequest;



    public OcrResponse requestOcr(MultipartFile multipartFile) {
        validateFile(multipartFile);
        try {
            String format = getFormat(multipartFile.getOriginalFilename());
            Map<String, Object> image = Map.of(
                "format", format,
                "name", "document"
            );

            Map<String, Object> message = Map.of(
                "version", "V2",
                "requestId", UUID.randomUUID().toString(),
                "timestamp", System.currentTimeMillis(),
                "images", List.of(image)
            );

            String messageJson = objectMapper.writeValueAsString(message);

            HttpHeaders messageHeaders = new HttpHeaders();
            messageHeaders.setContentType(MediaType.APPLICATION_JSON);

            ByteArrayResource fileResource = new ByteArrayResource(multipartFile.getBytes()) {
                @Override
                public String getFilename() {
                    return multipartFile.getOriginalFilename();
                }
            };

            HttpHeaders fileHeaders = new HttpHeaders();
            fileHeaders.setContentType(MediaType.parseMediaType(Objects.requireNonNullElse(
                multipartFile.getContentType(), MediaType.APPLICATION_OCTET_STREAM_VALUE)));

            MultiValueMap<String, Object> multipartBody = new LinkedMultiValueMap<>();
            multipartBody.add("message", new HttpEntity<>(messageJson, messageHeaders));
            multipartBody.add("file", new HttpEntity<>(fileResource, fileHeaders));

            RestClient restClient = restClientBuilder.build();

            String responseBody = restClient.post()
                .uri(ocrRequest.getInvokeUrl())
                .header("X-OCR-SECRET", ocrRequest.getSecretKey())
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .body(multipartBody)
                .retrieve()
                .body(String.class);

            return objectMapper.readValue(responseBody, OcrResponse.class);

        } catch (IOException e) {
            throw new RuntimeException("OCR 요청 중 파일 읽기 오류가 발생했습니다.", e);
        }

    }

    private void validateFile(MultipartFile multipartFile) {
        if (multipartFile == null || multipartFile.isEmpty()) {
            throw new IllegalArgumentException("파일이 비어있습니다.");
        }

        if (multipartFile.getSize() > 10 * 1024 * 1024) {
            throw new IllegalArgumentException("파일 크기는 10MB를 초과할 수 없습니다.");
        }
    }


    private String getFormat(String filename) {

        if (filename == null || !filename.contains(".")) {
            throw new IllegalArgumentException("파일 확장자가 없습니다.");
        }

        String ext = filename.substring(filename.lastIndexOf(".") + 1).toLowerCase();
        if (!List.of("jpg", "jpeg", "png").contains(ext)) {
            throw new IllegalArgumentException("지원하지 않는 파일 형식입니다.");
        }
        return ext.equals("jpeg") ? "jpg" : ext;

    }
}
