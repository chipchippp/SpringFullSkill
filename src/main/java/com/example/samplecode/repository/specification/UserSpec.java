package com.example.samplecode.repository.specification;

import com.example.samplecode.dto.validator.Gender;
import com.example.samplecode.model.User;
import org.springframework.data.jpa.domain.Specification;

public class UserSpec {
    public static Specification<User> hasFirstName(String firstName) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("firstName"), "%" + firstName + "%");
    }

    public static Specification<User> notEqualGender(Gender gender) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.notEqual(root.get("gender"), Gender.MALE);
    }
}
