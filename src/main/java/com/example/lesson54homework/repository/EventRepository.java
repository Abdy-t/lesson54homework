package com.example.lesson54homework.repository;

import com.example.lesson54homework.model.Event;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface EventRepository  extends CrudRepository<Event, String> {
    Event getById(String id);
}
