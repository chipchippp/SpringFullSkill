package com.example.samplecode.controller;

import com.example.samplecode.dto.request.UserRequestDTO;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {

//    @GetMapping("/")
//    public List<UserRequestDTO> getUsers(
//            @RequestParam(value = "email", required = false) String email,
//            @RequestParam(value = "page", defaultValue = "0") int pageNo,
//            @RequestParam(value = "limit", defaultValue = "10") int pageSize) {
//        System.out.println("Get all users");
//        return List.of(
//                new UserRequestDTO("John" , "Doe", "jonh@gmail.com", "1234567890"),
//                new UserRequestDTO("Jane", "Doe", "jane@gmail.com", "0987654321"));
//    }
//
//    @GetMapping(value = "/{userId}", produces = "application/json")
//    public UserRequestDTO getUser(@PathVariable Long userId) {
//        System.out.println("User id: " + userId);
//        return new UserRequestDTO("John" , "Doe", "john@gmail.com", "1234567890");
//    }

    @PostMapping("/add")
//    @RequestMapping(method = RequestMethod.POST, value = "/", headers = "api-key=1234")
    public String createUser(@Valid @RequestBody UserRequestDTO userRequestDTO) {
        return "User created";
    }

    @PutMapping("/{userId}")
    public String updateUser(@PathVariable("userId") Long id, @Valid @RequestBody UserRequestDTO userRequestDTO) {
        System.out.println("User id: " + id);
        return "User updated";
    }

    @PatchMapping("/{userId}")
    public String changeStatus(@PathVariable("userId") Long id, @RequestParam(value = "status", required = false) boolean status) {
        System.out.println("User id: " + id);
        System.out.println("Status: " + status);
        return "Status changed";
    }

    @DeleteMapping("/{userId}")
    public String deleteUser(@PathVariable("userId") Long id) {
        System.out.println("User id: " + id);
        return "User deleted";
    }
}
