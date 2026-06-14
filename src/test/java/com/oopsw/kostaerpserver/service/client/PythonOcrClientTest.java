package com.oopsw.kostaerpserver.service.client;

import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.header;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

import com.oopsw.kostaerpserver.dto.ocr.PythonOcrResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestClient;

class PythonOcrClientTest {

    private static final String URL = "http://localhost/api/v1/business-document-ocr";

    private MockRestServiceServer server;
    private PythonOcrClient client;

    @BeforeEach
    void setUp() {
        RestClient.Builder builder = RestClient.builder();
        server = MockRestServiceServer.bindTo(builder).build();
        client = new PythonOcrClient(builder.build());
        ReflectionTestUtils.setField(client, "pythonOcrUrl", URL);
    }

    @Test
    void sendsFileAsMultipartPart() throws Exception {
        server.expect(requestTo(URL))
            .andExpect(header(
                HttpHeaders.CONTENT_TYPE,
                containsString(MediaType.MULTIPART_FORM_DATA_VALUE)
            ))
            .andExpect(content().string(containsString("name=\"file\"")))
            .andExpect(content().string(containsString("filename=\"registration.jpg\"")))
            .andRespond(withSuccess("""
                {
                  "status": "AUTO_ACCEPTED",
                  "businessRegistrationNumber": "0000000006",
                  "companyName": "김밥전문점",
                  "representativeName": "김사장",
                  "validityStartDate": "2025-01-01",
                  "validityEndDate": "2026-12-31",
                  "selectedCandidate": null,
                  "candidates": []
                }
                """, MediaType.APPLICATION_JSON));

        PythonOcrResponse response = client.extractBusinessNumber(
            new MockMultipartFile(
                "document",
                "registration.jpg",
                MediaType.IMAGE_JPEG_VALUE,
                new byte[]{1}
            )
        );

        assertEquals("0000000006", response.businessRegistrationNumber());
        assertEquals("2025-01-01", response.validityStartDate());
        server.verify();
    }
}
