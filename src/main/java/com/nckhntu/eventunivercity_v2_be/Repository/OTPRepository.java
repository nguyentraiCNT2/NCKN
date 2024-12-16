package com.nckhntu.eventunivercity_v2_be.Repository;

import com.nckhntu.eventunivercity_v2_be.Entity.OTP;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OTPRepository extends JpaRepository<OTP, Long> {
    @Query("SELECT o FROM OTP o WHERE o.user.id = :id")
    List<OTP> findByUser(@Param("id") Long id);
}
