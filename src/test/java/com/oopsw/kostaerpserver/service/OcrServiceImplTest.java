package com.oopsw.kostaerpserver.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.header;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.oopsw.kostaerpserver.dto.ocr.OcrRequest;
import com.oopsw.kostaerpserver.dto.ocr.OcrResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestClient;
import org.springframework.mock.web.MockMultipartFile;

class OcrServiceImplTest {

    private MockRestServiceServer server;
    private OcrServiceImpl ocrService;

    @BeforeEach
    void setUp() {
        RestClient.Builder restClientBuilder = RestClient.builder();
        server = MockRestServiceServer.bindTo(restClientBuilder).build();

        OcrRequest ocrRequest = new OcrRequest();
        ocrRequest.setInvokeUrl("https://ocr.example.com/general");
        ocrRequest.setSecretKey("test-secret");

        ocrService = new OcrServiceImpl(new ObjectMapper(), restClientBuilder, ocrRequest);
    }

    @Test
    void sendsMultipartRequestAndParsesResponse() {
        server.expect(requestTo("https://ocr.example.com/general"))
            .andExpect(method(HttpMethod.POST))
            .andExpect(header("X-OCR-SECRET", "test-secret"))
            .andExpect(content().contentTypeCompatibleWith(MediaType.MULTIPART_FORM_DATA))
            .andExpect(content().string(org.hamcrest.Matchers.containsString("name=\"message\"")))
            .andExpect(content().string(org.hamcrest.Matchers.containsString("name=\"file\"")))
            .andRespond(withSuccess("""
                {
                  "version": "V2",
                  "requestId": "test-request",
                  "timestamp": 1,
                  "images": []
                }
                """, MediaType.APPLICATION_JSON));

        MockMultipartFile file = new MockMultipartFile(
            "file",
            "document.png",
            MediaType.IMAGE_PNG_VALUE,
            "image-content".getBytes()
        );

        OcrResponse response = ocrService.requestOcr(file);

        assertEquals("V2", response.getVersion());
        assertEquals("test-request", response.getRequestId());
        server.verify();
    }
}
