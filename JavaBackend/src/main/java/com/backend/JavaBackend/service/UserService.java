package com.backend.JavaBackend.service;

import com.backend.JavaBackend.dto.response.ApiResponse;
import com.backend.JavaBackend.dto.request.UserCreationRequest;
import com.backend.JavaBackend.dto.request.UserUpdateRequest;
import com.backend.JavaBackend.dto.response.UserResponse;
import com.backend.JavaBackend.entity.User;
import com.backend.JavaBackend.exception.AppException;
import com.backend.JavaBackend.exception.ErrorCode;
import com.backend.JavaBackend.mapper.UserMapper;
import com.backend.JavaBackend.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.*;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserService {
    UserRepository userRepository;
    UserMapper userMapper;

    public UserResponse createUser(UserCreationRequest request) {
        if (userRepository.existsByUsername(request.getUsername()))
             throw new AppException(ErrorCode.USER_EXISTED);

        User user = userMapper.toUser(request);

        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);

        user.setPassword(passwordEncoder.encode(request.getPassword()));

        return userMapper.toUserResponse(userRepository.save(user));
    }

    public ApiResponse<List<UserResponse>> getUsers() {
        ApiResponse<List<UserResponse>> apiResponse = new ApiResponse<>();
        List<User> userList = userRepository.findAll();
        apiResponse.setMessage("Get users successfully.");
        List<UserResponse> userResponseList = userMapper.toUserResponseList(userList);
        apiResponse.setData(userResponseList);
        return apiResponse;
    }

    public ApiResponse<UserResponse> getUser(String id) {
        ApiResponse<UserResponse> apiResponse = new ApiResponse<>();
        if (userRepository.findById(id).isEmpty()) throw new AppException(ErrorCode.USER_NOT_EXISTED);
        apiResponse.setMessage("Get user successfully.");
        User user = userRepository.findById(id).get();
        apiResponse.setData(userMapper.toUserResponse(user));
        return apiResponse;
    }

    public ApiResponse<UserResponse> updateUser(String id, UserUpdateRequest request) {
        User user = userMapper.toUser(getUser(id).getData());
        ApiResponse<UserResponse> apiResponse = new ApiResponse<>();
        userMapper.updateUser(user, request);
        userRepository.save(user);
        UserResponse userResponse = userMapper.toUserResponse(user);
        apiResponse.setMessage("Update user successfully.");
        apiResponse.setData(userResponse);
        return apiResponse;
    }

    public String deleteUser(String id) {
        UserResponse userResponse = getUser(id).getData();
        userRepository.deleteById(id);
        return "User has been deleted.";
    }
}
