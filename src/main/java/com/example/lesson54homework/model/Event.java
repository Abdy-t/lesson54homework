package com.example.lesson54homework.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.util.UUID;

@Document(collection = "events")
@Data
@Builder
@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true)
@AllArgsConstructor
public class Event {
    @Id
    @Builder.Default
    private String id = UUID.randomUUID().toString();
    private LocalDate time;
    private String name;
    private String description;

}
