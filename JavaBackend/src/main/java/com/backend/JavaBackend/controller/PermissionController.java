package com.backend.JavaBackend.controller;

import com.backend.JavaBackend.dto.request.PermissionRequest;
import com.backend.JavaBackend.dto.response.ApiResponse;
import com.backend.JavaBackend.dto.response.PermissionResponse;
import com.backend.JavaBackend.service.PermissionService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/permissions")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class PermissionController {
    PermissionService permissionService;

    @PostMapping("/create")
    public ApiResponse<PermissionResponse> createPermission(@RequestBody PermissionRequest request) {
        return ApiResponse.<PermissionResponse>builder()
                .statusCode(200)
                .message("Create permission successfully.")
                .data(permissionService.create(request))
                .build();
    }

    @GetMapping
    public ApiResponse<List<PermissionResponse>> getAllPermissions() {
        return ApiResponse.<List<PermissionResponse>>builder()
                .statusCode(200)
                .message("Get permissions successfully.")
                .data(permissionService.getAllPermissions())
                .build();
    }

    @DeleteMapping("{permission}")
    public ApiResponse<Void> deletePermission(@PathVariable String permission) {
        permissionService.deletePermission(permission);
        return ApiResponse.<Void>builder()
                .statusCode(200)
                .message("Delete permission successfully.")
                .build();
    }
}
