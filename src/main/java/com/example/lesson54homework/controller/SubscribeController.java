package com.example.lesson54homework.controller;

import com.example.lesson54homework.dto.EventDTO;
import com.example.lesson54homework.model.Subscribe;
import com.example.lesson54homework.service.EventService;
import com.example.lesson54homework.service.SubscribeService;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/main")
public class SubscribeController {
    private final SubscribeService subscribeService;
    private final EventService eventService;

    public SubscribeController(SubscribeService subscribeService, EventService eventService) {
        this.subscribeService=subscribeService;
        this.eventService=eventService;
    }

    @GetMapping
    public Iterable<Subscribe> findAllEvents() {
        return subscribeService.findAllSubscribes();
    }

    @GetMapping("/{email}/subscribes")
    public Slice<EventDTO> myEvents(@PathVariable String email, Pageable pageable) {
        return subscribeService.getMySubscribes(email, pageable);
    }
    @PostMapping(path = "/{idEvent}/{email}")
    public boolean subscribeEvent(@PathVariable String idEvent, @PathVariable String email) {
        return subscribeService.subscribeEvent(idEvent, email);
    }

    @DeleteMapping(path = "/{idEvent}/{email}")
    public boolean deleteSubscribeEvent(@PathVariable String idEvent, @PathVariable String email) {
        return subscribeService.deleteSubscribeEvent(idEvent, email);
    }
}
