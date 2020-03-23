package com.example.lesson54homework.service;

import com.example.lesson54homework.dto.EventDTO;
import com.example.lesson54homework.model.Event;
import com.example.lesson54homework.model.Subscribe;
import com.example.lesson54homework.repository.EventRepository;
import com.example.lesson54homework.repository.SubscribeRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

@Service
public class SubscribeService {
    private final SubscribeRepository subscribeRepository;
    private final EventRepository eventRepository;

    public SubscribeService(SubscribeRepository subscribeRepository, EventRepository eventRepository) {
        this.subscribeRepository = subscribeRepository;
        this.eventRepository=eventRepository;
    }
    public Iterable<Subscribe> findAllSubscribes() {
        return subscribeRepository.findAll();
    }

    public Slice<EventDTO> getMySubscribes(String email, Pageable pageable) {
        var s = subscribeRepository.getByEmail(email);
        List<Event> events = new ArrayList<>();
        IntStream.range(0, s.getEvents().size()).forEachOrdered(i -> events.add(eventRepository.getById(s.getEvents().get(i).getId())));
        Page<Event> page = new PageImpl<>(events, pageable, events.size());
        return page.map(EventDTO::from);
    }
    public boolean subscribeEvent(String idEvent, String email) {
        LocalDate date = LocalDate.now();
        if (eventRepository.existsById(idEvent)) {
            Event e = eventRepository.getById(idEvent);
            if(date.isBefore(e.getTime())) {
                if (subscribeRepository.existsByEmail(email)) {
                    Subscribe s = subscribeRepository.getByEmail(email);
                    if (checkEvent(s.getEvents(), idEvent)) {
                        List<Event> events = new ArrayList<>();
                        List<LocalDate> dates = new ArrayList<>();
                        if (s.getEvents()!=null) {
                            IntStream.range(0, s.getEvents().size()).forEachOrdered(i -> {
                                events.add(s.getEvents().get(i));
                            });
                            IntStream.range(0, s.getTime().size()).forEachOrdered(i -> {
                                dates.add(s.getTime().get(i));
                            });
                        }
                        events.add(eventRepository.getById(idEvent));
                        dates.add(LocalDate.now());
                        s.setEvents(events);
                    s.setTime(dates);
                    updateSubscribe(s);
                    return true;
                    }
                }
            }
        }
        return false;
    }

    public boolean checkEvent(List<Event> events, String id) {
        List<Event> eventList = new ArrayList<>();
        if (events != null) {
            for (int i = 0; i < events.size(); i++) {
                eventList.add(eventRepository.getById(events.get(i).getId()));
            }
            for (Event a : eventList) {
                if (a.getId().equals(id))
                    return false;
            }
        }
        return true;
    }
    public void updateSubscribe(Subscribe subscribe) {
        var sUpdate = Subscribe.builder()
                .id(subscribe.getId())
                .events(subscribe.getEvents())
                .email(subscribe.getEmail())
                .time(subscribe.getTime())
                .build();
        subscribeRepository.save(sUpdate);
    }
    public boolean deleteSubscribeEvent(String idEvent, String email) {
        if (eventRepository.existsById(idEvent)) {
            if (subscribeRepository.existsByEmail(email)) {
                Subscribe s = subscribeRepository.getByEmail(email);
                List<Event> events = s.getEvents();
                List<LocalDate> dates = s.getTime();
                if (!checkEvent(events, idEvent)) {
                    for (int i = 0; i < dates.size(); i++) {
                        if(events.get(i).getId().equals(idEvent)){
                            dates.remove(i);
                        }
                    }
                    events.removeIf(i -> (i.getId().equals(idEvent)));
                    s.setEvents(events);
                    updateSubscribe(s);
                    return true;
                }
            }
        }
        return false;
    }
}
