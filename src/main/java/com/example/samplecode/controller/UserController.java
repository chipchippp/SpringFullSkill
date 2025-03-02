package com.example.samplecode.controller;

import com.example.samplecode.configuration.Translator;
import com.example.samplecode.dto.request.UserRequestDTO;
import com.example.samplecode.dto.response.ResponseData;
import com.example.samplecode.dto.response.ResponseError;
import com.example.samplecode.dto.response.UserDetailResponse;
import com.example.samplecode.exception.ResourceNotFoundException;
import com.example.samplecode.service.UserService;
import com.example.samplecode.util.UserStatus;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/user")
@Slf4j
@Tag(name = "User Controller", description = "User API")
public class UserController {
    private final UserService userService;

    @Operation(summary = "Advance search query by specifications", description = "Return list of users")
    @GetMapping(path = "/advance-search-with-specification", produces = APPLICATION_JSON_VALUE)
    public ResponseData<?> advanceSearchWithSpecifications(Pageable pageable,
                                                           @RequestParam(required = false) String[] user,
                                                           @RequestParam(required = false) String[] address) {
        return new ResponseData<>(HttpStatus.OK.value(), "users", userService.advanceSearchWithSpecifications(pageable, user, address));
    }

    @Operation(summary = "Get all users", description = "Get all users")
    @GetMapping("/list")
    public ResponseData<?> getAllUsers(
            @RequestParam(defaultValue = "0", required = false) int pageNo,
            @RequestParam(defaultValue = "10", required = false) int pageSize,
            @RequestParam(required = false) String sortBy
    ) {
        log.info("Get all users");
        return new ResponseData<>(HttpStatus.OK.value(), "User found", userService.getAllUsers(pageNo, pageSize, sortBy));
    }

    @Operation(summary = "Get all users WithSortByMultipleColumns", description = "Get all users")
    @GetMapping("/listWithSortByMultipleColumns")
    public ResponseData<?> getAllUserWithSortByMultipleColum(
            @RequestParam(defaultValue = "0", required = false) int pageNo,
            @RequestParam(defaultValue = "10", required = false) int pageSize,
            @RequestParam(required = false) String... sorts
    ) {
        log.info("Get all users WithSortByMultipleColumns");
        return new ResponseData<>(HttpStatus.OK.value(), "User found", userService.getAllUserWithSortByMultipleColum(pageNo, pageSize, sorts));
    }

    @Operation(summary = "Get list of users and search with paging and sorting by customize query", description = "Send a request via this API to get user list by pageNo, pageSize and sort by multiple column")
    @GetMapping("/list-user-and-search-with-paging-and-sorting")
    public ResponseData<?> getAllUsersAndSearchWithPagingAndSorting(@RequestParam(defaultValue = "0", required = false) int pageNo,
                                                                    @RequestParam(defaultValue = "20", required = false) int pageSize,
                                                                    @RequestParam(required = false) String search,
                                                                    @RequestParam(required = false) String sortBy) {
        log.info("Request get list of users and search with paging and sorting");
        return new ResponseData<>(HttpStatus.OK.value(), "users", userService.getAllUsersAndSearchWithPagingAndSorting(pageNo, pageSize, search, sortBy));
    }

    @Operation(summary = "Advance search query by criteria", description = "Send a request via this API to get user list by pageNo, pageSize and sort by multiple column")
    @GetMapping("/advance-search-with-criteria")
    public ResponseData<?> advanceSearchWithCriteria(@RequestParam(defaultValue = "0", required = false) int pageNo,
                                                     @RequestParam(defaultValue = "20", required = false) int pageSize,
                                                     @RequestParam(required = false) String sortBy,
                                                     @RequestParam(required = false) String address,
                                                     @RequestParam(required = false) String... search) {
        log.info("Request advance search query by criteria");
        return new ResponseData<>(HttpStatus.OK.value(), "users", userService.advanceSearchWithCriteria(pageNo, pageSize, sortBy, address, search));
    }

    @Operation(summary = "Get user by id", description = "Get user by id")
    @GetMapping("/{userId}")
    public ResponseData<UserDetailResponse> getUserId(@PathVariable @Min(1) long userId) {
        log.info("User id: " + userId);
        try {
            UserDetailResponse user = userService.getUserId(userId);
            return new ResponseData<>(HttpStatus.OK.value(), "User found", user);
        }catch (ResourceNotFoundException e){
            log.error("Error = {} ", e.getMessage(), e.getCause());
            return new ResponseError(HttpStatus.BAD_REQUEST.value(), e.getMessage());
        }
    }
    @Operation(summary = "Add user", description = "Add user")
    @PostMapping("/add")
    public ResponseData<Long> createUser(@Valid @RequestBody UserRequestDTO request) {
        log.info("Request add user = {} {}: ", request.getFirstName(), request.getLastName());
        try {
            long userId = userService.addUser(request);
            return new ResponseData<>(HttpStatus.CREATED.value(), Translator.toLocale("user.add.success"), userId);
        } catch (Exception e) {
            log.error("Error = {} ", e.getMessage(), e.getCause());
            return new ResponseError(HttpStatus.BAD_REQUEST.value(), "Add user fail");
        }
    }

    @Operation(summary = "Update user", description = "Update user")
    @PutMapping("/{userId}")
    public ResponseData<?> updateUser(@PathVariable @Min(1) long userId, @Valid @RequestBody UserRequestDTO request) {
        log.info("User id = {} ", userId);
        try {
            userService.updateUser(userId, request);
            return new ResponseData<>(HttpStatus.ACCEPTED.value(), Translator.toLocale("user.update.success"));
        }catch (ResourceNotFoundException e){
            log.error("Error = {} ", e.getMessage(), e.getCause());
            return new ResponseError(HttpStatus.BAD_REQUEST.value(), "Update user failed");
        }
    }

    @Operation(summary = "Change status", description = "Change status")
    @PatchMapping("/{userId}")
    public ResponseData<?> changeStatus(@Min(1) @PathVariable long userId, @RequestParam UserStatus status) {
        log.info("User id = {} ", userId);
        try {
            userService.changeUserStatus(userId, status);
            return new ResponseData<>(HttpStatus.ACCEPTED.value(), Translator.toLocale("user.change.success"));
        } catch (ResourceNotFoundException e){
            log.error("Error = {} ", e.getMessage(), e.getCause());
            return new ResponseError(HttpStatus.BAD_REQUEST.value(), "Change user failed");
        }
    }

    @Operation(summary = "Delete user", description = "Delete user")
    @DeleteMapping("/{userId}")
    public ResponseData<?> deleteUser(@PathVariable("userId") @Min(value = 1, message = "userId must be greater than 0") long id) {
        log.info("User id = {} ", id);
        try {
            userService.deleteUser(id);
            return new ResponseData<>(HttpStatus.NO_CONTENT.value(), Translator.toLocale("user.delete.success"));
        }catch (ResourceNotFoundException e){
            log.error("Error = {} ", e.getMessage(), e.getCause());
            return new ResponseError(HttpStatus.BAD_REQUEST.value(), "Delete user failed");
        }
    }
}
