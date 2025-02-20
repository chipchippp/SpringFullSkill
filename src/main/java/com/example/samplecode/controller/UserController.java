package com.example.samplecode.controller;

import com.example.samplecode.configuration.Translator;
import com.example.samplecode.dto.request.UserRequestDTO;
import com.example.samplecode.dto.response.ResponseData;
import com.example.samplecode.service.UserService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/user")
@Slf4j
public class UserController {
//    @Autowired
    private final UserService userService;


    @GetMapping("/list")
    @ResponseStatus(HttpStatus.OK)
    public ResponseData<List<UserRequestDTO>> getUsers(
            @RequestParam(defaultValue = "0", required = false) int pageNo,
            @Min(10) @RequestParam(defaultValue = "20", required = false) int pageSize) {
        log.info("Get all users");
        return new ResponseData<>(HttpStatus.OK.value(), "User found", List.of(
                new UserRequestDTO("John" , "Doe", "john@gmail.com", "1234567890"),
                new UserRequestDTO("Jane", "Doe", "jane@gmail.com", "0987654321")));
    }

    @GetMapping("/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseData<UserRequestDTO> getUser(@PathVariable @Min(1) Long userId) {
        log.info("User id: " + userId);
        return new ResponseData<>(HttpStatus.OK.value(), "User found", new UserRequestDTO("John" , "Doe", "john@gmail.com", "1234567890"));
    }

    @PostMapping("/add")
    public ResponseData<Integer> createUser(@Valid @RequestBody UserRequestDTO userRequestDTO) {
        log.info("Request add user = {} {}: ", userRequestDTO.getFirstName(), userRequestDTO.getLastName());
        try {
            userService.addUser(userRequestDTO);
            return new ResponseData<>(HttpStatus.CREATED.value(), Translator.toLocale("user.add.success"));
        } catch (Exception e) {
            return new ResponseData<>(HttpStatus.BAD_REQUEST.value(), Translator.toLocale("user.error"));
        }
    }

    @PutMapping("/{userId}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseData<?> updateUser(@PathVariable @Min(1) Long userId, @Valid @RequestBody UserRequestDTO userRequestDTO) {
        log.info("User id = {} ", userId);
        return new ResponseData<>(HttpStatus.ACCEPTED.value(), Translator.toLocale("user.update.success"));
    }

    @PatchMapping("/{userId}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseData<?> changeStatus(@Min(1) @PathVariable Long userId, @RequestParam(value = "status", required = false) boolean status) {
        log.info("User id = {} ", userId);
        return new ResponseData<>(HttpStatus.ACCEPTED.value(), Translator.toLocale("user.update.success"));
    }

    @DeleteMapping("/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseData<?> deleteUser(@PathVariable("userId") @Min(value = 1, message = "userId must be greater than 0") Long id) {
        log.info("User id = {} ", id);
        return new ResponseData<>(HttpStatus.NO_CONTENT.value(), Translator.toLocale("user.delete.success"));
    }
}
