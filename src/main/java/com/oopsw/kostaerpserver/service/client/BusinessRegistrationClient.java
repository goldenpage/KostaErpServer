package com.oopsw.kostaerpserver.service.client;

import com.oopsw.kostaerpserver.dto.ocr.BusinessRegistrationRequest;
import com.oopsw.kostaerpserver.dto.ocr.BusinessRegistrationResponse;
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

    public boolean exists(String businessNumber) {
        BusinessRegistrationResponse response = restClientBuilder.build()
            .post()
            .uri(businessRegistrationUrl)
            .contentType(MediaType.APPLICATION_JSON)
            .body(new BusinessRegistrationRequest(businessNumber))
            .retrieve()
            .body(BusinessRegistrationResponse.class);

        return response != null && response.exists();
    }
}