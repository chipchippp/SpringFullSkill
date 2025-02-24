package com.example.samplecode.service;

import com.example.samplecode.dto.request.UserRequestDTO;
import com.example.samplecode.dto.response.PageResponse;
import com.example.samplecode.dto.response.UserDetailResponse;
import com.example.samplecode.util.UserStatus;

import java.util.List;

public interface UserService {

    UserDetailResponse getUserId(long userId);

    PageResponse<?> getAllUsers(int pageNo, int pageSize, String sortBy);
    PageResponse<?> getAllUserWithSortByMultipleColum(int pageNo, int pageSize, String... sorts);


    long addUser(UserRequestDTO request);

    void updateUser(long userId, UserRequestDTO request);

    void deleteUser(long userId);

    void changeUserStatus(long userId, UserStatus status);
}
