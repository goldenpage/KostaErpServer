package com.oopsw.kostaerpserver.advice;

import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;

@Slf4j
@RestControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiErrorResponse> handleIllegalArgument(
        IllegalArgumentException exception
    ) {
        return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(new ApiErrorResponse(exception.getMessage()));
    }

    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<ApiErrorResponse> handleIllegalState(
        IllegalStateException exception
    ) {
        return ResponseEntity
            .status(HttpStatus.CONFLICT)
            .body(new ApiErrorResponse(exception.getMessage()));
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ApiErrorResponse> handleDataIntegrity(
        DataIntegrityViolationException exception
    ) {
        log.warn("데이터 무결성 오류가 발생했습니다.", exception);

        return ResponseEntity
            .status(HttpStatus.CONFLICT)
            .body(new ApiErrorResponse(
                "이미 처리되었거나 중복된 데이터입니다."
            ));
    }

    @ExceptionHandler({
        HttpMessageNotReadableException.class,
        MissingServletRequestPartException.class
    })
    public ResponseEntity<ApiErrorResponse> handleInvalidRequest(
        Exception exception
    ) {
        log.warn("잘못된 요청 형식입니다.", exception);

        return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(new ApiErrorResponse(
                "요청 형식이 올바르지 않습니다."
            ));
    }

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ResponseEntity<ApiErrorResponse> handleMaxUploadSize(
        MaxUploadSizeExceededException exception
    ) {
        return ResponseEntity
            .status(HttpStatus.PAYLOAD_TOO_LARGE)
            .body(new ApiErrorResponse(
                "업로드 가능한 파일 크기를 초과했습니다."
            ));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiErrorResponse> handleUnexpected(
        Exception exception
    ) {
        log.error("처리되지 않은 서버 오류가 발생했습니다.", exception);

        return ResponseEntity
            .status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(new ApiErrorResponse(
                "서버 처리 중 오류가 발생했습니다."
            ));
    }

    public record ApiErrorResponse(String message) {
    }
}