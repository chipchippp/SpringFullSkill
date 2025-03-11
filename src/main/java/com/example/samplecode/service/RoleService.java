package com.example.samplecode.service;

import com.example.samplecode.model.Role;
import com.example.samplecode.repository.RoleRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public record RoleService(RoleRepository roleRepository) {
    @PostConstruct
    public List<Role> getAll() {
        List<Role> roles = roleRepository.getAllByUserId(2l);
        return roles;
    }
}
