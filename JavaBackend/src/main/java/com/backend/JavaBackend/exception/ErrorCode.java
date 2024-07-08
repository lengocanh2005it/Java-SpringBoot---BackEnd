package com.backend.JavaBackend.exception;

import lombok.Getter;

@Getter
public enum ErrorCode {
    USER_EXISTED(409, "User has existed."),
    UNCATEGORIZED_EXCEPTION(999, "Uncategorized error."),
    USER_NOT_EXISTED(404, "User hasn't existed."),
    USERNAME_INVALID(400, "Username must be at least 3 characters."),
    PASSWORD_INVALID(400, "Password must be at least 8 characters."),
    FIRST_NAME_INVALID(400, "FirstName mustn't be blank!"),
    LAST_NAME_INVALID(400, "LastName mustn't be blank!");
    ;
    private final int statusCode;
    private final String message;

    ErrorCode(int statusCode, String message){
        this.message = message;
        this.statusCode = statusCode;
    }

}
