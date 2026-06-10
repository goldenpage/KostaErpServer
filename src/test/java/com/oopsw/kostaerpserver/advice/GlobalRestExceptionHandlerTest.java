package com.oopsw.kostaerpserver.advice;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.oopsw.kostaerpserver.dto.auth.ApiResponse;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

class GlobalRestExceptionHandlerTest {

    private final GlobalRestExceptionHandler handler = new GlobalRestExceptionHandler();

    @Test
    void returnsJsonBadRequestResponse() {
        ResponseEntity<ApiResponse> response =
            handler.handleBadRequest(new IllegalArgumentException("잘못된 요청입니다."));

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(false, response.getBody().isSuccess());
        assertEquals("잘못된 요청입니다.", response.getBody().getMessage());
    }

    @Test
    void returnsJsonPayloadTooLargeResponse() {
        ResponseEntity<ApiResponse> response = handler.handleMaxUploadSize();

        assertEquals(HttpStatus.PAYLOAD_TOO_LARGE, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(false, response.getBody().isSuccess());
    }
}
