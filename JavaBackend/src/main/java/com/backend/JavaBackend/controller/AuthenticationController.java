package com.backend.JavaBackend.controller;


import com.backend.JavaBackend.dto.request.AuthenticationRequest;
import com.backend.JavaBackend.dto.response.ApiResponse;
import com.backend.JavaBackend.dto.response.AuthenticationResponse;
import com.backend.JavaBackend.service.AuthenticationService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationController {
    AuthenticationService authenticationService;

    @PostMapping("/login")
    ApiResponse<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request) {
        boolean authenticated = authenticationService.authenticate(request);
        if (authenticated) {
            return ApiResponse.<AuthenticationResponse>builder()
                    .data(AuthenticationResponse.builder()
                            .authenticated(true)
                            .build())
                    .statusCode(200)
                    .message("Login successfully.")
                    .build();
        }
        return ApiResponse.<AuthenticationResponse>builder()
                .data(AuthenticationResponse.builder()
                        .authenticated(false)
                        .build())
                .statusCode(401)
                .message("Login failed.")
                .build();
    }
}
