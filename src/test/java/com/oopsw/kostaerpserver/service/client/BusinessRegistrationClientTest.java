package com.oopsw.kostaerpserver.service.client;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

import com.oopsw.kostaerpserver.dto.ocr.BusinessRegistrationValidationResult;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestClient;

class BusinessRegistrationClientTest {

    private static final String URL = "http://localhost/nts-businessman/v1/validate";
    private static final String BUSINESS_NUMBER = "0000000006";

    private MockRestServiceServer server;
    private BusinessRegistrationClient client;

    @BeforeEach
    void setUp() {
        RestClient.Builder builder = RestClient.builder();
        server = MockRestServiceServer.bindTo(builder).build();
        client = new BusinessRegistrationClient(builder);
        ReflectionTestUtils.setField(client, "businessRegistrationUrl", URL);
    }

    @Test
    void validateReturnsTrueForRegisteredBusinessNumber() {
        respondWith("01", "");

        assertTrue(validate().valid());
        server.verify();
    }

    @Test
    void validateReturnsFalseForUnregisteredBusinessNumber() {
        respondWith("02", "국세청에 등록되지 않은 사업자등록번호입니다.");

        BusinessRegistrationValidationResult result = validate();

        assertFalse(result.valid());
        assertTrue(result.message().contains("국세청"));
        server.verify();
    }

    @Test
    void validateThrowsForUnknownValidationCode() {
        respondWith("99", "알 수 없는 결과");

        assertThrows(IllegalStateException.class, this::validate);
        server.verify();
    }

    private BusinessRegistrationValidationResult validate() {
        return client.validate(
            BUSINESS_NUMBER,
            "20250101",
            "김사장",
            "김밥전문점"
        );
    }

    private void respondWith(String valid, String validMessage) {
        String response = """
            {
              "status_code": "OK",
              "request_cnt": 1,
              "valid_cnt": 1,
              "data": [
                {
                  "b_no": "%s",
                  "valid": "%s",
                  "valid_msg": "%s"
                }
              ]
            }
            """.formatted(BUSINESS_NUMBER, valid, validMessage);

        server.expect(requestTo(URL))
            .andExpect(content().json("""
                {
                  "businesses": [
                    {
                      "b_no": "0000000006",
                      "start_dt": "20250101",
                      "p_nm": "김사장",
                      "b_nm": "김밥전문점"
                    }
                  ]
                }
                """))
            .andRespond(withSuccess(response, MediaType.APPLICATION_JSON));
    }
}
