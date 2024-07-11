package com.backend.JavaBackend.controller;

import com.backend.JavaBackend.dto.request.RoleRequest;
import com.backend.JavaBackend.dto.response.ApiResponse;
import com.backend.JavaBackend.dto.response.RoleResponse;
import com.backend.JavaBackend.service.RoleService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/roles")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class RoleController {
    RoleService roleService;

    @PostMapping("/create")
    public ApiResponse<RoleResponse> createRole(@RequestBody RoleRequest request) {
        return ApiResponse.<RoleResponse>builder()
                .statusCode(200)
                .message("Create roles successfully.")
                .data(roleService.createRole(request))
                .build();
    }

    @GetMapping
    public ApiResponse<List<RoleResponse>> getAllRoles() {
        return ApiResponse.<List<RoleResponse>>builder()
                .statusCode(200)
                .message("Get roles successfully.")
                .data(roleService.getAllRoles())
                .build();
    }

    @DeleteMapping("{role}")
    public ApiResponse<Void> deleteRole(@PathVariable String role) {
        roleService.deleteRole(role);
        return ApiResponse.<Void>builder()
                .statusCode(200)
                .message("Delete role successfully.")
                .build();
    }
}
