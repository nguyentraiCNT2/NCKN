package com.nckhntu.eventunivercity_v2_be.Controller;


import com.nckhntu.eventunivercity_v2_be.Model.DTO.EventDTO;
import com.nckhntu.eventunivercity_v2_be.Service.EventService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/events")
public class EventController {

    private final EventService eventService;

    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    // Lấy tất cả sự kiện với phân trang
    @GetMapping
    public ResponseEntity<?> getAllEvents(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        try {
            List<EventDTO> events = eventService.getAllEvents(page, size);
            return ResponseEntity.ok(events);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("message", e.getMessage(), "status", HttpStatus.INTERNAL_SERVER_ERROR.value()));
        }

    }

    // Tìm kiếm sự kiện theo tên với phân trang
    @GetMapping("/search-by-name")
    public ResponseEntity<?> getEventsByName(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam String name) {
        try {
        List<EventDTO> events = eventService.getEventsByName(page, size, name);
        return ResponseEntity.ok(events);
    } catch (Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("message", e.getMessage(), "status", HttpStatus.INTERNAL_SERVER_ERROR.value()));
    }
    }

    // Lấy sự kiện theo trạng thái phê duyệt với phân trang
    @GetMapping("/approval-status")
    public ResponseEntity<?> getEventsByApprovalStatus(
            @RequestParam Boolean isApproved,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        try {
        List<EventDTO> events = eventService.getEventsByApprovalStatus(isApproved, page, size);
        return ResponseEntity.ok(events);
    } catch (Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("message", e.getMessage(), "status", HttpStatus.INTERNAL_SERVER_ERROR.value()));
    }
    }

    // Lọc sự kiện theo địa điểm, tên, thể loại, và thời gian bắt đầu
    @GetMapping("/filter")
    public ResponseEntity<?> searchEvents(
            @RequestParam(required = false) String location,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) Timestamp startTime,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        try {
        List<EventDTO> events = eventService.searchEvents(location, name, category, startTime, page, size);
        return ResponseEntity.ok(events);
    } catch (Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("message", e.getMessage(), "status", HttpStatus.INTERNAL_SERVER_ERROR.value()));
    }
    }

    // Thêm mới sự kiện
    @PostMapping
    public ResponseEntity<?> createEvent(@RequestBody EventDTO eventDTO) {
        try {
        EventDTO createdEvent = eventService.createEvent(eventDTO);
        return ResponseEntity.ok(createdEvent);
    } catch (Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("message", e.getMessage(), "status", HttpStatus.INTERNAL_SERVER_ERROR.value()));
    }
    }

    // Sửa sự kiện
    @PutMapping("/{id}")
    public ResponseEntity<?> updateEvent(
            @PathVariable Long id,
            @RequestBody EventDTO eventDetails) {
        try {
        EventDTO updatedEvent = eventService.updateEvent(id, eventDetails);
        return ResponseEntity.ok(updatedEvent);
    } catch (Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("message", e.getMessage(), "status", HttpStatus.INTERNAL_SERVER_ERROR.value()));
    }
    }

    // Xóa sự kiện
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteEvent(@PathVariable Long id) {
        try {
        eventService.deleteEvent(id);
            return ResponseEntity.status(HttpStatus.OK).body(Map.of("message", "Event deleted"));
    } catch (Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("message", e.getMessage(), "status", HttpStatus.INTERNAL_SERVER_ERROR.value()));
    }
    }
}
