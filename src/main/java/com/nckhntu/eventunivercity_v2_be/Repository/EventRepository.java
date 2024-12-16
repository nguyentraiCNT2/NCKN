package com.nckhntu.eventunivercity_v2_be.Repository;


import com.nckhntu.eventunivercity_v2_be.Entity.Event;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {
@Query("select e from Event e WHERE e.name like %:name%")
    List<Event> findByName(@Param("name") String name, Pageable pageable);

    // Truy vấn sự kiện theo trạng thái phê duyệt
    @Query("SELECT e FROM Event e WHERE e.isApproved = :isApproved")
    List<Event> findByIsApproved(Boolean isApproved, Pageable pageable);

    // Lọc sự kiện theo địa điểm, tên, thể loại, và thời gian bắt đầu
    @Query("SELECT e FROM Event e WHERE e.location LIKE %:location% AND e.name LIKE %:name% AND e.category LIKE %:category% AND e.startTime >= :startTime")
    List<Event> findByLocationContainingAndNameContainingAndCategoryContainingAndStartTimeAfter(String location, String name, String category, Timestamp startTime, Pageable pageable);
}