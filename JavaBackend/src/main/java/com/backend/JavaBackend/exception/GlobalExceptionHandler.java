package com.backend.JavaBackend.exception;

import com.backend.JavaBackend.dto.response.ApiResponse;
import jakarta.validation.ConstraintViolation;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.text.ParseException;
import java.util.*;

@ControllerAdvice
public class GlobalExceptionHandler {

    private static final String MIN_ATTRIBUTE = "min";

    @ExceptionHandler(value = AppException.class)
    ResponseEntity<ApiResponse<String>> handlingAppException(AppException exception) {
        ErrorCode errorCode = exception.getErrorCode();
        return ResponseEntity.status(errorCode.getHttpStatusCode())
                .body(ApiResponse.<String>builder()
                        .statusCode(errorCode.getStatusCode())
                        .message(errorCode.getMessage())
                        .build());
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    ResponseEntity<ApiResponse<String>> handlingNotValidException(MethodArgumentNotValidException exception) {
        String enumKey = Objects.requireNonNull(exception.getFieldError()).getDefaultMessage();

        ErrorCode errorCode = ErrorCode.valueOf(enumKey);
        Map<String, Object> attributes = null;
        try {
            ConstraintViolation<?> constraintViolation = exception.getBindingResult()
                    .getAllErrors().stream()
                    .findFirst()
                    .map(error -> error.unwrap(ConstraintViolation.class))
                    .orElse(null);
            if (constraintViolation != null) {
                attributes = constraintViolation.getConstraintDescriptor().getAttributes();
            }
        } catch (IllegalAccessError ignored) {}
        return ResponseEntity.status(errorCode.getHttpStatusCode())
                .body(ApiResponse.<String>builder()
                        .statusCode(errorCode.getStatusCode())
                        .message(Objects.nonNull(attributes) ?
                                mapAttribute(errorCode.getMessage(), attributes) :
                                errorCode.getMessage())
                        .build());
    }

    @ExceptionHandler(value = ParseException.class)
    ResponseEntity<ApiResponse<String>> handleJOSEException(ParseException parseException) {
        return ResponseEntity.status(ErrorCode.TOKEN_INVALID.getHttpStatusCode())
                .body(ApiResponse.<String>builder()
                        .statusCode(ErrorCode.TOKEN_INVALID.getStatusCode())
                        .message(ErrorCode.TOKEN_INVALID.getMessage())
                        .build());
    }

    @ExceptionHandler(value = AccessDeniedException.class)
    ResponseEntity<ApiResponse<String>>
            handleAccessDeniedException(AccessDeniedException accessDeniedException) {
        return ResponseEntity.status(ErrorCode.UNAUTHORIZED.getHttpStatusCode())
                .body(ApiResponse.<String>builder()
                        .statusCode(ErrorCode.UNAUTHORIZED.getStatusCode())
                        .message(ErrorCode.UNAUTHORIZED.getMessage())
                        .build());
    }

    @ExceptionHandler(value = Exception.class)
    ResponseEntity<ApiResponse<String>> handlingException(Exception exception) {
        return ResponseEntity.status(ErrorCode.UNCATEGORIZED_EXCEPTION.getHttpStatusCode())
                .body(ApiResponse.<String>builder()
                        .statusCode(ErrorCode.UNCATEGORIZED_EXCEPTION.getStatusCode())
                        .message(ErrorCode.UNCATEGORIZED_EXCEPTION.getMessage())
                        .build());
    }

    private String mapAttribute(String message, Map<String, Object> attributes) {
        String minValue = String.valueOf(attributes.get(MIN_ATTRIBUTE));
        return message.replace("{" + MIN_ATTRIBUTE + "}", minValue);
    }
}
