package com.oopsw.kostaerpserver.advice.restcontroller;


import com.oopsw.kostaerpserver.dto.ocr.BusinessRegistrationRequest;
import com.oopsw.kostaerpserver.dto.ocr.BusinessRegistrationResponse;
import com.oopsw.kostaerpserver.dto.ocr.PythonOcrResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class OcrRestController {
    private final RestClient.Builder restClient;


    //@Value("${external-api.python-ocr-url}")
    private String pythonOcrUrl;

    //@Value("${external-api.business-registration-url}")
    private String businessRegistrationUrl;


    public PythonOcrResponse extractBusinessNumber(MultipartFile file) {
        try {
            ByteArrayResource resource = new ByteArrayResource(file.getBytes()) {
                @Override
                public String getFilename() {
                    return file.getOriginalFilename();
                }
            };

            MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
            body.add("file", resource);

            PythonOcrResponse response = restClient.build()
                .post()
                .uri(pythonOcrUrl)
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .body(body)
                .retrieve()
                .body(PythonOcrResponse.class);

            if (response == null) {
                throw new RuntimeException("Python OCR 응답이 없습니다.");
            }

            return response;
        } catch (IOException | RestClientException exception) {
            throw new RuntimeException("Python OCR 요청에 실패했습니다.", exception);
        }
    }


    public boolean exists(String businessNumber) {
        try {
            BusinessRegistrationResponse response = restClient.build()
                .post()
                .uri(businessRegistrationUrl)
                .contentType(MediaType.APPLICATION_JSON)
                .body(new BusinessRegistrationRequest(businessNumber))
                .retrieve()
                .body(BusinessRegistrationResponse.class);

            if (response == null) {
                throw new RuntimeException("Nest.js 검증 응답이 없습니다.");
            }

            return response.exists();
        } catch (RestClientException exception) {
            throw new RuntimeException("Nest.js 검증 요청에 실패했습니다.", exception);
        }
    }
}
