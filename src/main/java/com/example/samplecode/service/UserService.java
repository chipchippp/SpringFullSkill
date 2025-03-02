package com.example.samplecode.service;

import com.example.samplecode.dto.request.UserRequestDTO;
import com.example.samplecode.dto.response.PageResponse;
import com.example.samplecode.dto.response.UserDetailResponse;
import com.example.samplecode.util.UserStatus;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UserService {

    UserDetailResponse getUserId(long userId);

    PageResponse<?> getAllUsers(int pageNo, int pageSize, String sortBy);
    PageResponse<?> getAllUserWithSortByMultipleColum(int pageNo, int pageSize, String... sorts);
    PageResponse<?> getAllUsersAndSearchWithPagingAndSorting(int pageNo, int pageSize, String search, String sortBy);
    PageResponse<?> advanceSearchWithCriteria(int pageNo, int pageSize, String sortBy, String address, String... search);
    PageResponse<?> advanceSearchWithSpecifications(Pageable pageable, String[] user, String[] address);

    long addUser(UserRequestDTO request);

    void updateUser(long userId, UserRequestDTO request);

    void deleteUser(long userId);

    void changeUserStatus(long userId, UserStatus status);
}
