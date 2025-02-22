package com.example.samplecode.service.impl;

import com.example.samplecode.dto.request.AddressDTO;
import com.example.samplecode.dto.request.UserRequestDTO;
import com.example.samplecode.dto.response.UserDetailResponse;
import com.example.samplecode.exception.ResourceNotFoundException;
import com.example.samplecode.model.Address;
import com.example.samplecode.model.User;
import com.example.samplecode.repository.UserRepository;
import com.example.samplecode.service.UserService;
import com.example.samplecode.util.UserStatus;
import com.example.samplecode.util.UserType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public long addUser(UserRequestDTO request) {
        User user = User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .dateOfBirth(request.getDateOfBirth())
                .gender(request.getGender())
                .phone(request.getPhone())
                .email(request.getEmail())
                .username(request.getUsername())
                .password(request.getPassword())
                .type(UserType.valueOf(request.getType().toUpperCase()))
                .status(request.getStatus())
                .addresses(convertAddress(request.getAddresses()))
                .build();
        request.getAddresses().forEach(a ->
                user.saveAddress(Address.builder()
                        .apartmentNumber(a.getApartmentNumber())
                        .floor(a.getFloor())
                        .street(a.getStreet())
                        .streetNumber(a.getStreetNumber())
                        .city(a.getCity())
                        .country(a.getCountry())
                        .building(a.getBuilding())
                        .addressType(a.getAddressType())
                        .build()));
        userRepository.save(user);
        log.info("User {} added successfully", user.getId());
        return user.getId();
    }

    @Override
    public void updateUser(long userId, UserRequestDTO request) {
        User user = getUserById(userId);
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setDateOfBirth(request.getDateOfBirth());
        user.setGender(request.getGender());
        user.setPhone(request.getPhone());
        if (!request.getEmail().equals(user.getEmail())) {
            user.setEmail(request.getEmail());
        }
        user.setUsername(request.getUsername());
        user.setPassword(request.getPassword());
        user.setType(UserType.valueOf(request.getType().toUpperCase()));
        user.setStatus(request.getStatus());
        user.setAddresses(convertAddress(request.getAddresses()));
        userRepository.save(user);
        log.info("User {} updated successfully", user.getId());
    }

    @Override
    public void deleteUser(long userId) {
        userRepository.deleteById(userId);
        log.info("User {} deleted successfully", userId);
    }

    @Override
    public void changeUserStatus(long userId, UserStatus status) {
        User user = getUserById(userId);
        user.setStatus(status);
        userRepository.save(user);
        log.info("User {} status changed to {}", user.getId(), status);
    }

    @Override
    public UserDetailResponse getUser(long userId) {
        User user = getUserById(userId);
        return UserDetailResponse.builder()
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .phone(user.getPhone())
                .email(user.getEmail())
                .build();
    }

    @Override
    public List<UserDetailResponse> getAllUsers(int pageNo, int pageSize) {
        return List.of();
    }

    private Set<Address> convertAddress(Set<AddressDTO> addresses) {
        Set<Address> result = new HashSet<>();
        addresses.forEach(a ->
                result.add(Address.builder()
                        .apartmentNumber(a.getApartmentNumber())
                        .floor(a.getFloor())
                        .street(a.getStreet())
                        .streetNumber(a.getStreetNumber())
                        .city(a.getCity())
                        .country(a.getCountry())
                        .building(a.getBuilding())
                        .addressType(a.getAddressType())
                        .build()));
        return result;
    }

    private User getUserById(long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }
}
