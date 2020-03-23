package com.example.lesson54homework.repository;

import com.example.lesson54homework.model.Subscribe;
import org.springframework.data.repository.CrudRepository;

public interface SubscribeRepository  extends CrudRepository<Subscribe, String>  {
    boolean existsByEmail(String email);
    Subscribe getByEmail(String email);
}
