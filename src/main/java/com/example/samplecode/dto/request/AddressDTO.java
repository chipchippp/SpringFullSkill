package com.example.samplecode.dto.request;

import com.example.samplecode.model.User;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

@Getter
public class AddressDTO {
    @NotBlank(message = "apartmentNumber must not be blank")
    private String apartmentNumber;
    @NotBlank(message = "floor must not be blank")
    private String floor;
    @NotBlank(message = "building must not be blank")
    private String building;

    @NotBlank(message = "streetNumber must not be blank")
    private String streetNumber;
    @NotBlank(message = "street must not be blank")
    private String street;
    @NotBlank(message = "city must not be blank")
    private String city;
    @NotBlank(message = "country must not be blank")
    private String country;

    @NotBlank(message = "addressType must not be blank")
    private Integer addressType;
}
