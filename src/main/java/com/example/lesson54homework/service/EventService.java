package com.example.lesson54homework.service;

import com.example.lesson54homework.dto.EventDTO;
import com.example.lesson54homework.model.Event;
import com.example.lesson54homework.repository.EventRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class EventService {
    private final EventRepository eventRepository;

    public EventService(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    public Optional<EventDTO> findById(String id) {
        return eventRepository.findById(id).map(EventDTO::from);
    }

    public Iterable<Event> findAllEvents() {
       return eventRepository.findAll();
    }
}
