package com.backend.JavaBackend.exception;

import com.backend.JavaBackend.dto.response.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.text.ParseException;
import java.util.*;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(value = AppException.class)
    ResponseEntity<ApiResponse<String>> handlingAppException(AppException exception) {
        ErrorCode errorCode = exception.getErrorCode();
        return ResponseEntity.badRequest().body(ApiResponse.<String>builder()
                        .statusCode(errorCode.getStatusCode())
                        .message(errorCode.getMessage())
                .build());
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    ResponseEntity<ApiResponse<String>> handlingNotValidException(MethodArgumentNotValidException exception) {
        String enumKey = Objects.requireNonNull(exception.getFieldError()).getDefaultMessage();
        ErrorCode errorCode = ErrorCode.valueOf(enumKey);
        return ResponseEntity.badRequest().body(ApiResponse.<String>builder()
                        .statusCode(errorCode.getStatusCode())
                        .message(errorCode.getMessage())
                .build());
    }

    @ExceptionHandler(value = ParseException.class)
    ResponseEntity<ApiResponse<String>> handleJOSEException(ParseException joseException) {
        return ResponseEntity.badRequest().body(ApiResponse.<String>builder()
                        .statusCode(ErrorCode.TOKEN_INVALID.getStatusCode())
                        .message(ErrorCode.TOKEN_INVALID.getMessage())
                .build());
    }

    @ExceptionHandler(value = Exception.class)
    ResponseEntity<ApiResponse<String>> handlingException(Exception exception) {
        return ResponseEntity.badRequest().body(ApiResponse.<String>builder()
                        .statusCode(ErrorCode.UNCATEGORIZED_EXCEPTION.getStatusCode())
                        .message(ErrorCode.UNCATEGORIZED_EXCEPTION.getMessage())
                .build());
    }
}
