package com.example.samplecode.service;

import com.example.samplecode.dto.request.UserRequestDTO;
import com.example.samplecode.dto.response.UserDetailResponse;
import com.example.samplecode.util.UserStatus;

import java.util.List;

public interface UserService {
    long addUser(UserRequestDTO request);

    void updateUser(long userId, UserRequestDTO request);

    void deleteUser(long userId);

    void changeUserStatus(long userId, UserStatus status);

    UserDetailResponse getUser(long userId);

    List<UserDetailResponse> getAllUsers(int pageNo, int pageSize);
}
