package com.oopsw.kostaerpserver.dto.ocr;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

class BusinessRegistrationResponseTest {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void validationResultReturnsTrueWhenValidCodeIs01() throws Exception {
        BusinessRegistrationResponse response = readResponse("01", "");

        assertTrue(response.validationResult("0000000006").valid());
    }

    @Test
    void validationResultReturnsFalseWhenValidCodeIs02() throws Exception {
        BusinessRegistrationResponse response = readResponse(
            "02",
            "국세청에 등록되지 않은 사업자등록번호입니다."
        );

        assertFalse(response.validationResult("0000000006").valid());
        assertTrue(response.validationResult("0000000006").message().contains("국세청"));
    }

    @Test
    void validationResultThrowsWhenValidationCodeIsUnknown() throws Exception {
        BusinessRegistrationResponse response = readResponse("99", "알 수 없는 결과");

        assertThrows(
            IllegalStateException.class,
            () -> response.validationResult("0000000006")
        );
    }

    @Test
    void validationResultThrowsWhenRequestedBusinessNumberIsMissing() throws Exception {
        BusinessRegistrationResponse response = readResponse("01", "");

        assertThrows(
            IllegalStateException.class,
            () -> response.validationResult("1111111111")
        );
    }

    private BusinessRegistrationResponse readResponse(String valid, String validMessage)
        throws Exception {
        String json = """
            {
              "status_code": "OK",
              "request_cnt": 1,
              "valid_cnt": 1,
              "data": [
                {
                  "b_no": "0000000006",
                  "valid": "%s",
                  "valid_msg": "%s",
                  "request_param": {
                    "b_no": "0000000006",
                    "start_dt": "20250101",
                    "p_nm": "김사장",
                    "b_nm": "김밥전문점"
                  },
                  "status": {
                    "b_no": "0000000006",
                    "b_stt": "계속사업자"
                  }
                }
              ]
            }
            """.formatted(valid, validMessage);

        return objectMapper.readValue(json, BusinessRegistrationResponse.class);
    }
}
