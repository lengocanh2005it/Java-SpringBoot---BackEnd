package com.backend.JavaBackend.service;

import com.backend.JavaBackend.dto.request.UserCreationRequest;
import com.backend.JavaBackend.dto.request.UserUpdateRequest;
import com.backend.JavaBackend.entity.User;
import com.backend.JavaBackend.exception.AppException;
import com.backend.JavaBackend.exception.ErrorCode;
import com.backend.JavaBackend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public User createUser(UserCreationRequest request) {
        User user = new User();

        if (userRepository.existsByUsername(request.getUsername()))
             throw new AppException(ErrorCode.USER_EXISTED);

        user.setUsername(request.getUsername());
        user.setPassword(request.getPassword());
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setBirthday(request.getBirthday());
        return userRepository.save(user);
    }

    public List<User> getUsers() {
        return userRepository.findAll();
    }

    public User getUser(String id) {
        return userRepository.findById(id).orElseThrow(() ->
                new AppException(ErrorCode.USER_NOT_EXISTED));
    }

    public User updateUser(String id, UserUpdateRequest request) {
        User user = getUser(id);
        user.setPassword(request.getPassword());
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setBirthday(request.getBirthday());
        return userRepository.save(user);
    }

    public void deleteUser(String id) {
        User user = getUser(id);
        if (user != null) {
            userRepository.deleteById(id);
        }
    }
}
