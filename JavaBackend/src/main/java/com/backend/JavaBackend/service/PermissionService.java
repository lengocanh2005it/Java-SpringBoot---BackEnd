package com.backend.JavaBackend.service;


import com.backend.JavaBackend.dto.request.PermissionRequest;
import com.backend.JavaBackend.dto.response.PermissionResponse;
import com.backend.JavaBackend.entity.Permission;
import com.backend.JavaBackend.exception.AppException;
import com.backend.JavaBackend.exception.ErrorCode;
import com.backend.JavaBackend.mapper.PermissionMapper;
import com.backend.JavaBackend.repository.PermissionRepository;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PermissionService {
    PermissionRepository permissionRepository;
    PermissionMapper permissionMapper;

    public PermissionResponse create(PermissionRequest request) {
        Permission permission = permissionMapper.toPermission(request);
        return permissionMapper.toPermissionResponse(permissionRepository.save(permission));
    }

    public List<PermissionResponse> getAllPermissions() {
        var permissions = permissionRepository.findAll();
        return permissions.stream().map(permissionMapper::toPermissionResponse).toList();
    }

    public void deletePermission(String permission) {
        if (permissionRepository.findById(permission).isPresent()){
            permissionRepository.deleteById(permission);
        }
        throw new AppException(ErrorCode.UNCATEGORIZED_EXCEPTION);
    }
}
