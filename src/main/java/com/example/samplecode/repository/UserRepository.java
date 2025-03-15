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

    @Query(value = "select r.name from Role r inner join UserHasRole ur on r.id = ur.user.id where ur.id= :userId")
    List<String> findAllRolesByUserId(Long userId);
}
