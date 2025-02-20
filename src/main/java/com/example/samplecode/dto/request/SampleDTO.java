package com.example.samplecode.dto.request;

import lombok.Getter;

import java.io.Serializable;

@Getter
public class SampleDTO implements Serializable {
    private Integer id;
    private String name;
}
