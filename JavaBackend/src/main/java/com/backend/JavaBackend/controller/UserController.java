package com.backend.JavaBackend.controller;

import com.backend.JavaBackend.dto.response.ApiResponse;
import com.backend.JavaBackend.dto.request.UserCreationRequest;
import com.backend.JavaBackend.dto.request.UserUpdateRequest;
import com.backend.JavaBackend.dto.response.UserResponse;
import com.backend.JavaBackend.service.UserService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import java.util.*;


@Controller
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserController {
    UserService userService;

    @GetMapping
    public ApiResponse<List<UserResponse>> getUsers() {
        return userService.getUsers();
    }

    @GetMapping("/{userId}")
    public ApiResponse<UserResponse> getUser(@PathVariable("userId") String userId) {
        return userService.getUser(userId);
    }

    @PostMapping("/create")
    public ApiResponse<UserResponse> createUser(@RequestBody @Valid UserCreationRequest request) {
        ApiResponse<UserResponse> apiResponse = new ApiResponse<>();
        apiResponse.setData(userService.createUser(request));
        apiResponse.setMessage("Create user successfully.");
        return apiResponse;
    }

    @PutMapping("/{userId}")
    public ApiResponse<UserResponse> updateUser(@PathVariable("userId") String userId, @RequestBody UserUpdateRequest request){
        return userService.updateUser(userId, request);
    }

    @DeleteMapping("/{userId}")
    public ApiResponse<String> deleteUser(@PathVariable("userId") String userId) {
        ApiResponse<String> apiResponse = new ApiResponse<>();
        apiResponse.setMessage(userService.deleteUser(userId));
        return apiResponse;
    }
}
