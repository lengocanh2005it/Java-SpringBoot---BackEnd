package com.backend.JavaBackend.exception;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public enum ErrorCode {
    EMPTY_USERS(200, "Empty users.",
            HttpStatus.OK),
    EMPTY_INVALIDATED_TOKEN(200, "Empty invalidated token.",
            HttpStatus.OK),
    USER_EXISTED(400, "User has existed.",
            HttpStatus.BAD_REQUEST),
    UNAUTHENTICATED(401, "Unauthenticated.",
            HttpStatus.UNAUTHORIZED),
    UNAUTHORIZED(403, "You don't have permission.",
            HttpStatus.FORBIDDEN),
    TOKEN_INVALID(400, "Invalid Token.",
            HttpStatus.BAD_REQUEST),
    UNCATEGORIZED_EXCEPTION(500, "Uncategorized error.",
            HttpStatus.INTERNAL_SERVER_ERROR),
    USER_NOT_EXISTED(404, "User hasn't existed.",
            HttpStatus.NOT_FOUND),
    BIRTHDAY_INVALID(400, "Your age must be at least {min}.",
            HttpStatus.BAD_REQUEST),
    USERNAME_INVALID(400, "Username must be at least 3 characters.",
            HttpStatus.BAD_REQUEST),
    PASSWORD_INVALID(400, "Password must be at least 8 characters.",
            HttpStatus.BAD_REQUEST),
    FIRST_NAME_INVALID(400, "Firstname mustn't be blank.",
            HttpStatus.BAD_REQUEST),
    LAST_NAME_INVALID(400, "Lastname mustn't be blank.",
            HttpStatus.BAD_REQUEST);
    ;
    int statusCode;
    String message;
    HttpStatusCode httpStatusCode;

    ErrorCode(int statusCode, String message, HttpStatusCode httpStatusCode){
        this.message = message;
        this.statusCode = statusCode;
        this.httpStatusCode = httpStatusCode;
    }

}
