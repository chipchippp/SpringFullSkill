package com.example.samplecode.service.impl;

import com.example.samplecode.configuration.Translator;
import com.example.samplecode.dto.request.AddressDTO;
import com.example.samplecode.dto.request.UserRequestDTO;
import com.example.samplecode.dto.response.PageResponse;
import com.example.samplecode.dto.response.UserDetailResponse;
import com.example.samplecode.dto.validator.Gender;
import com.example.samplecode.exception.ResourceNotFoundException;
import com.example.samplecode.model.Address;
import com.example.samplecode.model.User;
import com.example.samplecode.repository.SearchRepository;
import com.example.samplecode.repository.UserRepository;
import com.example.samplecode.repository.specification.UserSpec;
import com.example.samplecode.repository.specification.UserSpecification;
import com.example.samplecode.repository.specification.UserSpecificationsBuilder;
//import com.example.samplecode.service.MailService;
import com.example.samplecode.service.UserService;
import com.example.samplecode.util.UserStatus;
import com.example.samplecode.util.UserType;
import jakarta.mail.MessagingException;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.UnsupportedEncodingException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.example.samplecode.util.AppConst.SEARCH_SPEC_OPERATOR;
import static com.example.samplecode.util.AppConst.SORT_BY;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final SearchRepository searchRepository;
    //    private final MailService mailService;
    private final KafkaTemplate<String, String> kafkaTemplate;

    @Override
    public UserDetailsService getUserDetailsService() {
        return username -> userRepository.findUserByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(Translator.toLocale("user.not.found")));
    }

    @Override
    public PageResponse<?> getAllUsers(int pageNo, int pageSize, String sortBy) {
        int page = 0;
        if (pageNo > 0) {
            page = pageNo - 1;
        }

        List<Sort.Order> sorts = new ArrayList<>();

//        nếu có value
        if (StringUtils.hasLength(sortBy)) {
            Pattern pattern = Pattern.compile("(\\w+?)(:)(.*)");
            Matcher matcher = pattern.matcher(sortBy);
            if (matcher.find()) {
                if (matcher.group(3).equalsIgnoreCase("asc")) {
                    sorts.add(new Sort.Order(Sort.Direction.ASC, matcher.group(1)));
                } else {
                    sorts.add(new Sort.Order(Sort.Direction.DESC, matcher.group(1)));
                }
            }
        }

        Pageable pageable = PageRequest.of(page, pageSize, Sort.by(sorts));
        Page<User> users = userRepository.findAll(pageable);
        return convertToPageResponse(users, pageable);
    }

    @Override
    public PageResponse<?> getAllUserWithSortByMultipleColum(int pageNo, int pageSize, String... sorts) {
        int page = 0;
        if (pageNo > 0) {
            page = pageNo - 1;
        }

        List<Sort.Order> orders = new ArrayList<>();

//        nếu có value
        for (String sortBy : sorts) {
            Pattern pattern = Pattern.compile(SORT_BY);
            Matcher matcher = pattern.matcher(sortBy);
            if (matcher.find()) {
                if (matcher.group(3).equalsIgnoreCase("asc")) {
                    orders.add(new Sort.Order(Sort.Direction.ASC, matcher.group(1)));
                } else {
                    orders.add(new Sort.Order(Sort.Direction.DESC, matcher.group(1)));
                }
            }
        }

        Pageable pageable = PageRequest.of(page, pageSize, Sort.by(orders));
        Page<User> users = userRepository.findAll(pageable);
        return convertToPageResponse(users, pageable);
    }

    private PageResponse<?> convertToPageResponse(Page<User> users, Pageable pageable) {
        List<UserDetailResponse> response = users.stream().map(user -> UserDetailResponse.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .phone(user.getPhone())
                .build()).toList();
        return PageResponse.builder()
                .page(pageable.getPageNumber())
                .size(pageable.getPageSize())
                .total(users.getTotalPages())
                .items(response)
                .build();
    }

    @Override
    public PageResponse<?> getAllUsersAndSearchWithPagingAndSorting(int pageNo, int pageSize, String search, String sortBy) {
        return searchRepository.getAllUsersAndSearchWithPagingAndSorting(pageNo, pageSize, search, sortBy);
    }

    @Override
    public PageResponse<?> advanceSearchWithCriteria(int pageNo, int pageSize, String sortBy, String address, String... search) {
        return searchRepository.searchUserByCriteria(pageNo, pageSize, sortBy, address, search);
    }

    @Override
    public PageResponse<?> advanceSearchWithSpecifications(Pageable pageable, String[] user, String[] address) {
        log.info("getUsersBySpecifications");

        if (user != null && address != null) {
            return searchRepository.searchUserByCriteriaWithJoin(pageable, user, address);
        } else if (user != null) {
            UserSpecificationsBuilder builder = new UserSpecificationsBuilder();

            Pattern pattern = Pattern.compile(SEARCH_SPEC_OPERATOR);
            for (String s : user) {
                Matcher matcher = pattern.matcher(s);
                if (matcher.find()) {
                    builder.with(matcher.group(1), matcher.group(2), matcher.group(3), matcher.group(4), matcher.group(5));
                    System.out.println("Params: " + builder.params);
                }
            }

            Page<User> users = userRepository.findAll(Objects.requireNonNull(builder.build()), pageable);

            return convertToPageResponse(users, pageable);
        }

        return convertToPageResponse(userRepository.findAll(pageable), pageable);
    }

    @Override
    public User getByUsername(String userName) {
        return userRepository.findUserByUsername(userName).orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    @Override
    public User getUserByEmail(String email) {
        return userRepository.findUserByEmail(email).orElseThrow(() -> new ResourceNotFoundException("Email not found"));
    }

    @Override
    public long saveUser(User user) {
        userRepository.save(user);
        return user.getId();
    }


    @Override
    public UserDetailResponse getUserId(long userId) {
        User user = getUserById(userId);
        return UserDetailResponse.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .phone(user.getPhone())
                .email(user.getEmail())
                .build();
    }

    @Override
    public long addUser(UserRequestDTO request) throws MessagingException, UnsupportedEncodingException {
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
                .addresses(convertToAddress(request.getAddresses()))
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

//        if (user.getId() != null){
//            kafkaTemplate.send("confirm-account-topic", String.format("email=%s,id=%s,code=%s", user.getEmail(), user.getId(), "code@123"));
//        }
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
        user.setAddresses(convertToAddress(request.getAddresses()));
        userRepository.save(user);
        log.info("User {} updated successfully", user.getId());
    }

    @Override
    public void changeUserStatus(long userId, UserStatus status) {
        User user = getUserById(userId);
        user.setStatus(status);
        userRepository.save(user);
        log.info("User {} status changed to {}", user.getId(), status);
    }

    @Override
    public void confirmUser(long userId, String secretCode) {
        User user = getUserById(userId);
        user.setStatus(UserStatus.ACTIVE);
        userRepository.save(user);
        log.info("User {} confirmed successfully", user.getId());
    }

    @Override
    public void deleteUser(long userId) {
        userRepository.deleteById(userId);
        log.info("User {} deleted successfully", userId);
    }

    private Set<Address> convertToAddress(Set<AddressDTO> addresses) {
        Set<Address> result = new HashSet<>();
        addresses.forEach(a ->
                result.add(Address.builder()
                        .apartmentNumber(a.getApartmentNumber())
                        .floor(a.getFloor())
                        .building(a.getBuilding())
                        .streetNumber(a.getStreetNumber())
                        .street(a.getStreet())
                        .city(a.getCity())
                        .country(a.getCountry())
                        .addressType(a.getAddressType())
                        .build())
        );
        return result;
    }

    private User getUserById(long userId) {
        return userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }
}
