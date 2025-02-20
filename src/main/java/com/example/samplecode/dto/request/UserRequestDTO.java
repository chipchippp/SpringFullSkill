package com.example.samplecode.dto.request;

import com.example.samplecode.dto.validator.*;
import com.example.samplecode.model.Address;
import com.example.samplecode.util.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import static com.example.samplecode.dto.validator.Gender.*;

//@Setter
//@Getter
//@Data
//@NoArgsConstructor
//@AllArgsConstructor
//@Setter
//@Getter
public class UserRequestDTO implements Serializable {
    @NotBlank(message = "First name must not be blank")
    private String firstName;
    @NotNull(message = "Last name must not be null")
    private String lastName;
    @Email(message = "Email invalid format")
    private String email;

//    @Pattern(regexp = "^[0-9]{10}$", message = "Phone number must be 10 digits")
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

    @NotEmpty(message = "Permissions must not be empty")
    private Set<Address> address;

//    @Pattern(regexp = "^ACTIVE|INACTIVE|DELETED$", message = "Invalid status")
    @EnumPattern(name = "status", regexp = "^ACTIVE|INACTIVE|DELETED$", message = "Invalid status")
    private UserStatus status;

    @GenderSubset(anyOf = {MALE, FEMALE, OTHER})
    private Gender gender;

    @NotNull(message = "Type must not be null")
    @EnumValue(name = "type", enumClass = UserType.class, message = "Invalid type")
    private String type;

    public UserRequestDTO() {
    }

    public UserRequestDTO(String firstName, String lastName, String email, String phone) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
    }

    public UserRequestDTO(String firstName, String lastName, String email, String phone, Date dateOfBirth, String username, String password, Set<Address> address, UserStatus status) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
        this.dateOfBirth = dateOfBirth;
        this.username = username;
        this.password = password;
        this.address = address;
        this.status = status;
    }

    public @NotBlank(message = "First name must not be blank") String getFirstName() {
        return firstName;
    }

    public void setFirstName(@NotBlank(message = "First name must not be blank") String firstName) {
        this.firstName = firstName;
    }

    public @NotNull(message = "Last name must not be null") String getLastName() {
        return lastName;
    }

    public void setLastName(@NotNull(message = "Last name must not be null") String lastName) {
        this.lastName = lastName;
    }

    public @Email(message = "Email invalid format") String getEmail() {
        return email;
    }

    public void setEmail(@Email(message = "Email invalid format") String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public @NotNull(message = "Date of birth must not be null") Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(@NotNull(message = "Date of birth must not be null") Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public @NotNull(message = "Username must not be null") String getUsername() {
        return username;
    }

    public void setUsername(@NotNull(message = "Username must not be null") String username) {
        this.username = username;
    }

    public @NotNull(message = "Password must not be null") String getPassword() {
        return password;
    }

    public void setPassword(@NotNull(message = "Password must not be null") String password) {
        this.password = password;
    }

    public @NotEmpty(message = "Permissions must not be empty") Set<Address> getAddress() {
        return address;
    }

    public void setAddress(@NotEmpty(message = "Permissions must not be empty") Set<Address> address) {
        this.address = address;
    }

    public @EnumPattern(name = "status", regexp = "^ACTIVE|INACTIVE|DELETED$", message = "Invalid status") UserStatus getStatus() {
        return status;
    }

    public void setStatus(@EnumPattern(name = "status", regexp = "^ACTIVE|INACTIVE|DELETED$", message = "Invalid status") UserStatus status) {
        this.status = status;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public @NotNull(message = "Type must not be null") @EnumValue(name = "type", enumClass = UserType.class, message = "Invalid type") String getType() {
        return type;
    }

    public void setType(@NotNull(message = "Type must not be null") @EnumValue(name = "type", enumClass = UserType.class, message = "Invalid type") String type) {
        this.type = type;
    }
}
