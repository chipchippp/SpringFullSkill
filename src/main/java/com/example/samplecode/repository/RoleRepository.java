package com.example.samplecode.repository;

import com.example.samplecode.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {
    @Query("SELECT r FROM Role r INNER JOIN UserHasRole ur ON r.id = ur.role.id WHERE ur.user.id = :userId")
    List<Role> getAllByUserId(Long userId);
}
