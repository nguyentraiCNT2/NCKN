package com.nckhntu.eventunivercity_v2_be.Service;

import com.nckhntu.eventunivercity_v2_be.Model.DTO.EventDTO;
import org.springframework.data.domain.Page;

import java.sql.Timestamp;
import java.util.List;

public interface EventService {

    // Hiển thị tất cả sự kiện có phân trang
    List<EventDTO> getAllEvents(int page, int size);
    List<EventDTO> getEventsByName(int page, int size, String name);

    // Hiển thị sự kiện theo trạng thái isApproved
    List<EventDTO> getEventsByApprovalStatus(Boolean isApproved, int page, int size);

    // Lọc sự kiện theo địa điểm, tên, thể loại, và thời gian bắt đầu
    List<EventDTO> searchEvents(String location, String name, String category, Timestamp startTime, int page, int size);

    // Thêm mới sự kiện
    EventDTO createEvent(EventDTO event);

    // Sửa sự kiện
    EventDTO updateEvent(Long id, EventDTO eventDetails);

    // Xóa sự kiện
    void deleteEvent(Long id);
}
