package com.nckhntu.eventunivercity_v2_be.Repository;

import com.nckhntu.eventunivercity_v2_be.Entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<Users, Long> {
    @Query("SELECT u from Users u WHERE u.username = :input OR u.email = :input")
    Optional<Users> findByUsernameOrEmail(@Param("input") String input);

    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
}
