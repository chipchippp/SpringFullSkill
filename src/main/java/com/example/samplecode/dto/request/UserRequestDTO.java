package com.example.samplecode.dto.request;

import com.example.samplecode.util.PhoneNumber;
import com.example.samplecode.util.UserStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

//@Setter
//@Getter
//@Data
//@NoArgsConstructor
//@AllArgsConstructor
@Setter
@Getter
public class UserRequestDTO implements Serializable {
    @NotBlank(message = "First name must not be blank")
    private String firstName;
    @NotNull(message = "Last name must not be null")
    private String lastName;
    @Email(message = "Email invalid format")
    private String email;
    @Pattern(regexp = "^[0-9]{10}$", message = "Phone number must be 10 digits")
    private String phone;

    @NotNull(message = "Date of birth must not be null")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @JsonFormat(pattern = "MM/dd/yyyy")
    private Date dateOfBirth;

    @NotEmpty(message = "Permissions must not be empty")
    private List<String> permissions;

    @PhoneNumber
    private String phoneNumber;

    private UserStatus status;

    public UserRequestDTO(String firstName, String lastName, String email, String phone) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
    }

    public UserRequestDTO() {
    }

}
