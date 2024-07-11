package com.backend.JavaBackend.controller;


import com.backend.JavaBackend.dto.request.AuthenticationRequest;
import com.backend.JavaBackend.dto.request.IntrospectRequest;
import com.backend.JavaBackend.dto.request.LogoutRequest;
import com.backend.JavaBackend.dto.response.ApiResponse;
import com.backend.JavaBackend.dto.response.AuthenticationResponse;
import com.backend.JavaBackend.dto.response.IntrospectResponse;
import com.backend.JavaBackend.service.AuthenticationService;
import com.nimbusds.jose.JOSEException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;

@Controller
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationController {
    private static final Logger log = LoggerFactory.getLogger(AuthenticationController.class);
    AuthenticationService authenticationService;

    @PostMapping("/login")
    ApiResponse<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request) {
        var result = authenticationService.authenticate(request);
        return ApiResponse.<AuthenticationResponse>builder()
                .data(result)
                .statusCode(200)
                .build();
    }

    @PostMapping("/logout")
    ApiResponse<Void> logOut(@RequestBody LogoutRequest request) throws ParseException, JOSEException {
           authenticationService.logout(request);
           return ApiResponse.<Void>builder()
                   .statusCode(200)
                   .message("Log out token successfully.")
                   .build();
    }

    @PostMapping("/introspect")
    ApiResponse<IntrospectResponse> authenticate(@RequestBody IntrospectRequest request)
            throws ParseException, JOSEException {
        var data = authenticationService.introspect(request);
        return ApiResponse.<IntrospectResponse>builder()
                .data(data)
                .statusCode(200)
                .build();
    }

    @DeleteMapping("/delete")
    ApiResponse<Void> deleteInvalidatedTokens() {
        authenticationService.deleteInvalidatedTokens();
        return ApiResponse.<Void>builder()
                .statusCode(200)
                .message("Delete invalidated tokens successfully.")
                .build();
    }
}
