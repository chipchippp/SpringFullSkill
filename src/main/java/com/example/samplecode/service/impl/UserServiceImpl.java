package com.example.samplecode.service.impl;

import com.example.samplecode.dto.request.UserRequestDTO;
import com.example.samplecode.exception.ResourceNotFoundException;
import com.example.samplecode.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Override
    public int addUser(UserRequestDTO userRequestDTO) {
        System.out.println("User added: " + userRequestDTO.getFirstName());
        if (!userRequestDTO.getFirstName().equals("John")) {
            throw new ResourceNotFoundException("User not found");
        }
        return 0;
    }
}
