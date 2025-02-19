package com.example.samplecode.controller;

import com.example.samplecode.dto.request.UserRequestDTO;
import com.example.samplecode.dto.response.ResponseData;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    @GetMapping("/")
    @ResponseStatus(HttpStatus.OK)
    public ResponseData<List<UserRequestDTO>> getUsers(
            @RequestParam(value = "email", required = false) String email,
            @RequestParam(value = "page", defaultValue = "0") int pageNo,
            @RequestParam(value = "limit", defaultValue = "10") int pageSize) {
        System.out.println("Get all users");
        return new ResponseData<>(HttpStatus.OK.value(), "User found", List.of(
                new UserRequestDTO("John" , "Doe", "john@gmail.com", "1234567890"),
                new UserRequestDTO("Jane", "Doe", "jane@gmail.com", "0987654321")));
    }

    @GetMapping("/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseData<UserRequestDTO> getUser(@PathVariable @Min(1) Long userId) {
        System.out.println("User id: " + userId);
        return new ResponseData<>(HttpStatus.OK.value(), "User found", new UserRequestDTO("John" , "Doe", "john@gmail.com", "1234567890"));
    }

    @PostMapping("/add")
    public ResponseData<Integer> createUser(@Valid @RequestBody UserRequestDTO userRequestDTO) {
        System.out.println("Request: " + userRequestDTO.getFirstName());
        return new ResponseData<>(HttpStatus.CREATED.value(), "User created successful", 1);
    }

    @PutMapping("/{userId}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseData<?> updateUser(@PathVariable("userId") Long id, @Valid @RequestBody UserRequestDTO userRequestDTO) {
        System.out.println("User id: " + id);
        return new ResponseData<>(HttpStatus.ACCEPTED.value(), "User updated successful", 1);
    }

    @PatchMapping("/{userId}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseData<?> changeStatus(@PathVariable("userId") Long id, @RequestParam(value = "status", required = false) boolean status) {
        System.out.println("User id: " + id);
        System.out.println("Status: " + status);
        return new ResponseData<>(HttpStatus.ACCEPTED.value(), "Status changed successful");
    }

    @DeleteMapping("/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseData<?> deleteUser(@PathVariable("userId") @Min(value = 1, message = "userId must be greater than 0") Long id) {
        System.out.println("User id: " + id);
        return new ResponseData<>(HttpStatus.NO_CONTENT.value(), "User deleted successful");
    }
}
