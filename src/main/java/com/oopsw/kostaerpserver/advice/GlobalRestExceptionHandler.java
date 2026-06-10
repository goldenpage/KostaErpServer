package com.oopsw.kostaerpserver.advice;

import com.oopsw.kostaerpserver.dto.auth.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

@RestControllerAdvice(basePackages = "com.oopsw.kostaerpserver.advice.restcontroller")
@Slf4j
public class GlobalRestExceptionHandler {

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiResponse> handleBadRequest(IllegalArgumentException exception) {
        return ResponseEntity.badRequest()
            .body(new ApiResponse(false, exception.getMessage()));
    }

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ResponseEntity<ApiResponse> handleMaxUploadSize() {
        return ResponseEntity.status(HttpStatus.PAYLOAD_TOO_LARGE)
            .body(new ApiResponse(false, "업로드 파일 크기는 10MB를 초과할 수 없습니다."));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse> handleUnexpectedException(Exception exception) {
        log.error("REST API 처리 중 예상하지 못한 오류가 발생했습니다.", exception);
        return ResponseEntity.internalServerError()
            .body(new ApiResponse(false, "서버 처리 중 오류가 발생했습니다."));
    }
}
