package com.nckhntu.eventunivercity_v2_be.Repository;

import com.nckhntu.eventunivercity_v2_be.Entity.OTPTemplate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OTPTemplateRepository extends JpaRepository<OTPTemplate, Long> {
    @Query("select ot from OTPTemplate  ot WHERE ot.title = :title")
    Optional<OTPTemplate> findByTitle(@Param("title") String title);
}
