package com.example.samplecode.service;

import com.example.samplecode.dto.request.UserRequestDTO;
import com.example.samplecode.dto.response.PageResponse;
import com.example.samplecode.dto.response.UserDetailResponse;
import com.example.samplecode.model.User;
import com.example.samplecode.util.UserStatus;
import jakarta.mail.MessagingException;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.io.UnsupportedEncodingException;
import java.util.List;

public interface UserService {

    UserDetailsService getUserDetailsService();

    UserDetailResponse getUserId(long userId);

    PageResponse<?> getAllUsers(int pageNo, int pageSize, String sortBy);
    PageResponse<?> getAllUserWithSortByMultipleColum(int pageNo, int pageSize, String... sorts);
    PageResponse<?> getAllUsersAndSearchWithPagingAndSorting(int pageNo, int pageSize, String search, String sortBy);
    PageResponse<?> advanceSearchWithCriteria(int pageNo, int pageSize, String sortBy, String address, String... search);
    PageResponse<?> advanceSearchWithSpecifications(Pageable pageable, String[] user, String[] address);

    User getByUsername(String userName);

    long addUser(UserRequestDTO request) throws MessagingException, UnsupportedEncodingException;

    void updateUser(long userId, UserRequestDTO request);

    void deleteUser(long userId);

    void changeUserStatus(long userId, UserStatus status);

    void confirmUser(long userId, String secretCode);
}
