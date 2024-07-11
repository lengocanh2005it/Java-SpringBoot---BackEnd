package com.backend.JavaBackend.mapper;

import com.backend.JavaBackend.dto.request.UserCreationRequest;
import com.backend.JavaBackend.dto.request.UserUpdateRequest;
import com.backend.JavaBackend.dto.response.UserResponse;
import com.backend.JavaBackend.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import java.util.*;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User toUser(UserCreationRequest request);
    User toUser(UserResponse response);

    //@Mapping(source = "firstName", target = "lastName")
    //@Mapping(target = "roles", ignore = true)
    UserResponse toUserResponse(User user);

    List<UserResponse> toUserResponseList(List<User> userList);

    @Mapping(target = "roles", ignore = true)
    void updateUser(@MappingTarget User user, UserUpdateRequest request);
}
