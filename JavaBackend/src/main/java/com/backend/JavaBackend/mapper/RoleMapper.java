package com.backend.JavaBackend.mapper;

import com.backend.JavaBackend.dto.request.RoleRequest;
import com.backend.JavaBackend.dto.response.RoleResponse;
import com.backend.JavaBackend.entity.Role;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface RoleMapper {
    @Mapping(target = "permissions", ignore = true)
    Role toRole(RoleRequest request);

    RoleResponse toRoleResponse(Role role);
}
