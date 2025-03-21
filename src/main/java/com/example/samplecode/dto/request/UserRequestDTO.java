package com.example.samplecode.dto.request;

import com.example.samplecode.dto.validator.*;
import com.example.samplecode.util.UserStatus;
import com.example.samplecode.util.UserType;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import static com.example.samplecode.dto.validator.Gender.*;

@Getter
public class UserRequestDTO implements Serializable {
    @NotBlank(message = "First name must not be blank")
    private String firstName;
    @NotNull(message = "Last name must not be null")
    private String lastName;
    @Email(message = "Email invalid format")
    private String email;

    @PhoneNumber
    private String phone;

    @NotNull(message = "Date of birth must not be null")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @JsonFormat(pattern = "MM/dd/yyyy")
    private Date dateOfBirth;

    @NotNull(message = "Username must not be null")
    private String username;
    @NotNull(message = "Password must not be null")
    private String password;

    @GenderSubset(anyOf = {MALE, FEMALE, OTHER})
    private Gender gender;

    @NotNull(message = "Type must not be null")
    @EnumValue(name = "type", enumClass = UserType.class, message = "Invalid type")
    private String type;

    @EnumPattern(name = "status", regexp = "^ACTIVE|INACTIVE|DELETED$", message = "Invalid status")
    private UserStatus status;

    @NotEmpty(message = "Address must not be empty")
    private Set<AddressDTO> addresses;
}