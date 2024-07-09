package com.backend.JavaBackend.exception;

import lombok.Getter;

@Getter
public enum ErrorCode {
    NOT_ALLOWED(403,"You mustn't access to other user info."),
    EMPTY_USERS(200, "Empty users."),
    USER_EXISTED(409, "User has existed."),
    UNAUTHENTICATED(500, "Unauthenticated."),
    TOKEN_INVALID(400, "Invalid Token."),
    UNCATEGORIZED_EXCEPTION(999, "Uncategorized error."),
    USER_NOT_EXISTED(404, "User hasn't existed."),
    USERNAME_INVALID(400, "Username must be at least 3 characters."),
    PASSWORD_INVALID(400, "Password must be at least 8 characters."),
    FIRST_NAME_INVALID(400, "Firstname mustn't be blank."),
    LAST_NAME_INVALID(400, "Lastname mustn't be blank.");
    ;
    private final int statusCode;
    private final String message;

    ErrorCode(int statusCode, String message){
        this.message = message;
        this.statusCode = statusCode;
    }

}
