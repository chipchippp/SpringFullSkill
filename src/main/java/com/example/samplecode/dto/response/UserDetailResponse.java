package com.example.samplecode.dto.response;

import lombok.*;

import java.io.Serializable;

@Getter
@Builder
public class UserDetailResponse implements Serializable {
    private String firstName;
    private String lastName;
    private String phone;
    private String email;
}
