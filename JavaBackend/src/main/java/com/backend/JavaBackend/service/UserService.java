package com.backend.JavaBackend.service;

import com.backend.JavaBackend.dto.request.UserCreationRequest;
import com.backend.JavaBackend.dto.request.UserUpdateRequest;
import com.backend.JavaBackend.dto.response.UserResponse;
import com.backend.JavaBackend.entity.User;
import com.backend.JavaBackend.enums.Role;
import com.backend.JavaBackend.exception.AppException;
import com.backend.JavaBackend.exception.ErrorCode;
import com.backend.JavaBackend.mapper.UserMapper;
import com.backend.JavaBackend.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.*;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserService {
    private static final Logger log = LoggerFactory.getLogger(UserService.class);
    UserRepository userRepository;
    UserMapper userMapper;
    PasswordEncoder passwordEncoder;

    public UserResponse createUser(UserCreationRequest request) {
        if (userRepository.existsByUsername(request.getUsername()))
             throw new AppException(ErrorCode.USER_EXISTED);

        User user = userMapper.toUser(request);
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        HashSet<String> roles = new HashSet<>();
        roles.add(Role.USER.name());

        user.setRoles(roles);
        return userMapper.toUserResponse(userRepository.save(user));
    }

    public List<UserResponse> getUsers() {
        if (userRepository.findAll().isEmpty())
            throw new AppException(ErrorCode.EMPTY_USERS);

        return userMapper.toUserResponseList(userRepository.findAll());
    }

    public UserResponse getUser(String id) {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        if (userRepository.findById(id).isEmpty()) {
            throw new AppException(ErrorCode.USER_NOT_EXISTED);
        } else if (userRepository.findByUsername(authentication.getName()).isPresent()) {
             if (userRepository.
                     findByUsername(authentication.getName())
                     .get().getRoles().contains("ADMIN")) {
                 return userMapper.toUserResponse(userRepository.findById(id).get());
             }
            if (Objects.equals(userRepository
                    .findByUsername(authentication.getName()).get().getId(), id)) {
                return userMapper.toUserResponse(userRepository
                        .findByUsername(authentication.getName()).get());
            }
            throw new AppException(ErrorCode.NOT_ALLOWED);
        }
        return null;
    }

    public UserResponse getUser() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        if (userRepository.findByUsername(authentication.getName()).isEmpty())  {
            throw new AppException(ErrorCode.USER_NOT_EXISTED);
        }
        return userMapper.toUserResponse(userRepository
                .findByUsername(authentication.getName()).get());
    }

    public UserResponse updateUser(String id, UserUpdateRequest request) {
        if (userRepository.findById(id).isEmpty())
            throw new AppException(ErrorCode.USER_NOT_EXISTED);

        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
        request.setPassword(passwordEncoder.encode(request.getPassword()));
        User user = userMapper.toUser(getUser(id));
        userMapper.updateUser(user, request);
        return userMapper.toUserResponse(userRepository.save(user));
    }

    public void deleteUser(String id) {
        if (userRepository.findById(id).isPresent()) {
            userRepository.deleteById(id);
        } else {
            throw new AppException(ErrorCode.USER_NOT_EXISTED);
        }
    }
}
