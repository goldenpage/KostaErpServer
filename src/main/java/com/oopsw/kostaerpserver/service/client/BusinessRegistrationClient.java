package com.oopsw.kostaerpserver.service.client;

import com.oopsw.kostaerpserver.dto.ocr.BusinessRegistrationRequest;
import com.oopsw.kostaerpserver.dto.ocr.BusinessRegistrationResponse;
import com.oopsw.kostaerpserver.dto.ocr.BusinessRegistrationValidationResult;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
@RequiredArgsConstructor
public class BusinessRegistrationClient {

    private final RestClient.Builder restClientBuilder;

    @Value("${external-api.business-registration-url}")
    private String businessRegistrationUrl;

    public BusinessRegistrationValidationResult validate(
        String businessNumber,
        String startDate,
        String representativeName,
        String companyName
    ) {
        BusinessRegistrationResponse response = restClientBuilder.build()
            .post()
            .uri(businessRegistrationUrl)
            .contentType(MediaType.APPLICATION_JSON)
            .body(BusinessRegistrationRequest.of(
                businessNumber,
                startDate,
                representativeName,
                companyName
            ))
            .retrieve()
            .body(BusinessRegistrationResponse.class);

        if (response == null) {
            throw new IllegalStateException("사업자등록번호 검증 서버 응답이 없습니다.");
        }

        return response.validationResult(businessNumber);
    }
}
