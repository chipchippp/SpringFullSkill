package com.example.samplecode.repository;


import com.example.samplecode.model.Role;
import com.example.samplecode.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {
    Optional<User> findUserByEmail(String email);
    Optional<User> findUserByUsername(String username);

    @Query("SELECT r FROM Role r INNER JOIN UserHasRole ur ON r.id = ur.role.id WHERE ur.user.id = :userId")
    List<Role> getAllByUserId(Long userId);
}
