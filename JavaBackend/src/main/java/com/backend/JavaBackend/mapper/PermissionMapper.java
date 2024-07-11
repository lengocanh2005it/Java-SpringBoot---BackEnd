package com.backend.JavaBackend.mapper;

import com.backend.JavaBackend.dto.request.PermissionRequest;
import com.backend.JavaBackend.dto.response.PermissionResponse;
import com.backend.JavaBackend.entity.Permission;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PermissionMapper {
    Permission toPermission(PermissionRequest request);
    PermissionResponse toPermissionResponse(Permission permission);
}
