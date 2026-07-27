package com.oopsw.kostaerpserver.service.client;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.BufferingClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestClient;

@Configuration
public class ExternalApiClientConfig {

    @Bean("pythonOcrRestClient")
    public RestClient pythonOcrRestClient(RestClient.Builder builder) {
        return builder.clone()
            .requestFactory(new BufferingClientHttpRequestFactory(
                new SimpleClientHttpRequestFactory()
            ))
            .build();
    }
}
