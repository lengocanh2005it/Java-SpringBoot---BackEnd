package com.backend.JavaBackend.controller;


import com.backend.JavaBackend.dto.request.AuthenticationRequest;
import com.backend.JavaBackend.dto.request.IntrospectRequest;
import com.backend.JavaBackend.dto.response.ApiResponse;
import com.backend.JavaBackend.dto.response.AuthenticationResponse;
import com.backend.JavaBackend.dto.response.IntrospectResponse;
import com.backend.JavaBackend.service.AuthenticationService;
import com.nimbusds.jose.JOSEException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;

@Controller
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationController {
    AuthenticationService authenticationService;

    @PostMapping("/login")
    ApiResponse<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request) {
        var result = authenticationService.authenticate(request);
        return ApiResponse.<AuthenticationResponse>builder()
                .data(result)
                .statusCode(200)
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
}
