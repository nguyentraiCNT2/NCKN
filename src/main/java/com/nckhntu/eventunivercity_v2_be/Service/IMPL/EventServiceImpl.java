package com.nckhntu.eventunivercity_v2_be.Service.IMPL;


import com.nckhntu.eventunivercity_v2_be.Entity.Event;
import com.nckhntu.eventunivercity_v2_be.Model.DTO.EventDTO;
import com.nckhntu.eventunivercity_v2_be.Repository.EventRepository;
import com.nckhntu.eventunivercity_v2_be.Repository.UserRepository;
import com.nckhntu.eventunivercity_v2_be.Service.EventService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Service
public class EventServiceImpl implements EventService {

    private final EventRepository eventRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    public EventServiceImpl(EventRepository eventRepository, UserRepository userRepository, ModelMapper modelMapper) {
        this.eventRepository = eventRepository;
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    // Hiển thị tất cả sự kiện có phân trang
    @Override
    public List<EventDTO> getAllEvents(int page, int size) {
        try {
            Pageable pageable = PageRequest.of(page, size);

            List<Event> events = eventRepository.findAll(pageable).getContent();
            if (events == null)
                throw new RuntimeException("Không có sự kiện nào");
            List<EventDTO> list = new ArrayList<>();
            events.stream().forEach(e -> {
                list.add(modelMapper.map(e, EventDTO.class));
            });
            return list;
        } catch (Exception e) {
            throw new RuntimeException( e.getMessage());
        }
    }

    @Override
    public List<EventDTO> getEventsByName(int page, int size, String name) {
        try {
            Pageable pageable = PageRequest.of(page, size);

            List<Event> events = eventRepository.findByName(name,pageable);
            if (events == null)
                throw new RuntimeException("Không có sự kiện nào");
            List<EventDTO> list = new ArrayList<>();
            events.stream().forEach(e -> {
                list.add(modelMapper.map(e, EventDTO.class));
            });
            return list;
        } catch (Exception e) {
            throw new RuntimeException( e.getMessage());
        }
    }

    // Hiển thị sự kiện theo trạng thái isApproved
    @Override
    public List<EventDTO> getEventsByApprovalStatus(Boolean isApproved, int page, int size) {
        try {
            Pageable pageable = PageRequest.of(page, size);
            List<Event> events = eventRepository.findByIsApproved(isApproved, pageable);
            if (events == null)
                throw new RuntimeException("Không có sự kiện nào");
            List<EventDTO> list = new ArrayList<>();
            events.stream().forEach(e -> {
                list.add(modelMapper.map(e, EventDTO.class));
            });
            return list;
        } catch (Exception e) {
            throw new RuntimeException( e.getMessage());
        }
    }

    // Lọc sự kiện theo địa điểm, tên, thể loại, và thời gian bắt đầu
    @Override
    public List<EventDTO> searchEvents(String location, String name, String category, Timestamp startTime, int page, int size) {
        try {
            Pageable pageable = PageRequest.of(page, size);
            List<Event> events = eventRepository.findByLocationContainingAndNameContainingAndCategoryContainingAndStartTimeAfter(location, name, category, startTime, pageable);
            if (events == null)
                throw new RuntimeException("Không có sự kiện nào");
            List<EventDTO> list = new ArrayList<>();
            events.stream().forEach(e -> {
                list.add(modelMapper.map(e, EventDTO.class));
            });
            return list;
        } catch (Exception e) {
            throw new RuntimeException( e.getMessage());
        }
    }

    // Thêm mới sự kiện
    @Override
    public EventDTO createEvent(EventDTO event) {
        try {
            event.setCreatedAt(new Timestamp(System.currentTimeMillis()));
            Event event1 = modelMapper.map(event, Event.class);
            event1 = eventRepository.save(event1);
            return modelMapper.map(event1, EventDTO.class);
        } catch (Exception e) {
            throw new RuntimeException( e.getMessage());
        }
    }

    // Sửa sự kiện
    @Override
    public EventDTO updateEvent(Long id, EventDTO eventDetails) {
        try {
            Event event = eventRepository.findById(id).orElseThrow(() -> new RuntimeException("Event not found"));
            event.setName(eventDetails.getName());
            event.setDescription(eventDetails.getDescription());
            event.setLocation(eventDetails.getLocation());
            event.setCategory(eventDetails.getCategory());
            event.setEventDate(eventDetails.getEventDate());
            event.setStartTime(eventDetails.getStartTime());
            event.setEndTime(eventDetails.getEndTime());
            event.setMaxParticipants(eventDetails.getMaxParticipants());
            event.setApproved(eventDetails.getApproved());
            event.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
            Event updatedEvent = eventRepository.save(event);
            return modelMapper.map(updatedEvent, EventDTO.class);
        } catch (Exception e) {
            throw new RuntimeException( e.getMessage());
        }
    }

    // Xóa sự kiện
    @Override
    public void deleteEvent(Long id) {
        try {
            Event event = eventRepository.findById(id).orElseThrow(() -> new RuntimeException("Event not found"));
            eventRepository.delete(event);
        } catch (Exception e) {
            throw new RuntimeException( e.getMessage());
        }
    }


}
