package com.nckhntu.eventunivercity_v2_be.Repository;

import com.nckhntu.eventunivercity_v2_be.Entity.Category;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    @Query("select c from Category c WHERE c.name like :name")
    List<Category> findByName(@Param("name") String name, Pageable pageable);
}
