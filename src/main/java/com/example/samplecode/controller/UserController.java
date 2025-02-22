package com.example.samplecode.controller;

import com.example.samplecode.configuration.Translator;
import com.example.samplecode.dto.request.UserRequestDTO;
import com.example.samplecode.dto.response.ResponseData;
import com.example.samplecode.dto.response.ResponseError;
import com.example.samplecode.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/user")
@Slf4j
@Tag(name = "User Controller", description = "User API")
public class UserController {
    private final UserService userService;

//    @Operation(summary = "Get all users", description = "Get all users")
//    @GetMapping("/list")
//    @ResponseStatus(HttpStatus.OK)
//    public ResponseData<List<UserRequestDTO>> getUsers(
//            @RequestParam(defaultValue = "0", required = false) int pageNo,
//            @Min(10) @RequestParam(defaultValue = "20", required = false) int pageSize) {
//        log.info("Get all users");
//
//        try {
//            userService.getAllUsers(pageNo, pageSize);
//        } catch (Exception e) {
//            return new ResponseData<>(HttpStatus.BAD_REQUEST.value(), "User not found");
//        }
//
//        return new ResponseData<>(HttpStatus.OK.value(), "User found", List.of(
//                new UserRequestDTO("John", "Doe", "john@gmail.com", "1234567890"),
//                new UserRequestDTO("Jane", "Doe", "jane@gmail.com", "0987654321")));
//    }
//
//    @Operation(summary = "Get user by id", description = "Get user by id")
//    @GetMapping("/{userId}")
//    @ResponseStatus(HttpStatus.OK)
//    public ResponseData<UserRequestDTO> getUser(@PathVariable @Min(1) Long userId) {
//        log.info("User id: " + userId);
//        return new ResponseData<>(HttpStatus.OK.value(), "User found", new UserRequestDTO("John", "Doe", "john@gmail.com", "1234567890"));
//    }

    @Operation(summary = "Add user", description = "Add user")
    @PostMapping("/add")
    public ResponseData<Long> createUser(@Valid @RequestBody UserRequestDTO user) {
        log.info("Request add user = {} {}: ", user.getFirstName(), user.getLastName());
        try {
            long userId = userService.addUser(user);
            return new ResponseData<>(HttpStatus.CREATED.value(), Translator.toLocale("user.add.success"), userId);
        } catch (Exception e) {
            log.error("Error = {} ", e.getMessage(), e.getCause());
            return new ResponseError(HttpStatus.BAD_REQUEST.value(), Translator.toLocale("user.error"));
        }
    }

    @Operation(summary = "Update user", description = "Update user")
    @PutMapping("/{userId}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseData<?> updateUser(@PathVariable @Min(1) Long userId, @Valid @RequestBody UserRequestDTO userRequestDTO) {
        log.info("User id = {} ", userId);
        return new ResponseData<>(HttpStatus.ACCEPTED.value(), Translator.toLocale("user.update.success"));
    }

    @Operation(summary = "Change status", description = "Change status")
    @PatchMapping("/{userId}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseData<?> changeStatus(@Min(1) @PathVariable Long userId, @RequestParam(value = "status", required = false) boolean status) {
        log.info("User id = {} ", userId);
        return new ResponseData<>(HttpStatus.ACCEPTED.value(), Translator.toLocale("user.update.success"));
    }

    @Operation(summary = "Delete user", description = "Delete user")
    @DeleteMapping("/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseData<?> deleteUser(@PathVariable("userId") @Min(value = 1, message = "userId must be greater than 0") Long id) {
        log.info("User id = {} ", id);
        return new ResponseData<>(HttpStatus.NO_CONTENT.value(), Translator.toLocale("user.delete.success"));
    }
}
