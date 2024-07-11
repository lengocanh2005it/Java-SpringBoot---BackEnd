package com.backend.JavaBackend.service;


import com.backend.JavaBackend.dto.request.RoleRequest;
import com.backend.JavaBackend.dto.response.RoleResponse;
import com.backend.JavaBackend.entity.Role;
import com.backend.JavaBackend.exception.AppException;
import com.backend.JavaBackend.exception.ErrorCode;
import com.backend.JavaBackend.mapper.RoleMapper;
import com.backend.JavaBackend.repository.PermissionRepository;
import com.backend.JavaBackend.repository.RoleRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class RoleService {
    RoleRepository roleRepository;
    PermissionRepository permissionRepository;
    RoleMapper roleMapper;

    public RoleResponse createRole(RoleRequest request) {
        Role role = roleMapper.toRole(request);
        var permissions = permissionRepository.findAllById(request.getPermissions());
        role.setPermissions(new HashSet<>(permissions));

        return roleMapper.toRoleResponse(roleRepository.save(role));
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public List<RoleResponse> getAllRoles() {
        return roleRepository.findAll()
                .stream()
                .map(roleMapper::toRoleResponse)
                .toList();
    }

    public void deleteRole(String role) {
        if (roleRepository.findById(role).isPresent()) {
            roleRepository.deleteById(role);
        }
        throw new AppException(ErrorCode.UNCATEGORIZED_EXCEPTION);
    }
}
