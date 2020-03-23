package com.example.lesson54homework.controller;

import com.example.lesson54homework.dto.EventDTO;
import com.example.lesson54homework.model.Event;
import com.example.lesson54homework.service.EventService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/events")
public class EventController {
    private final EventService eventService;

    public EventController(EventService eventService) {
        this.eventService=eventService;
    }

    @GetMapping
    public Iterable<Event> findAllEvents() {
        return eventService.findAllEvents();
    }

    @GetMapping("/{id}")
    public Optional<EventDTO> getEvent(@PathVariable String id) {
        return eventService.findById(id);
    }
}
