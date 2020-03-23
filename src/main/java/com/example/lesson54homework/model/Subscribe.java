package com.example.lesson54homework.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Document(collection = "subscribes")
@Data
@Builder
@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true)
@AllArgsConstructor
public class Subscribe {
    @Id
    @Builder.Default
    private String id = UUID.randomUUID().toString();
    @DBRef
    private List<Event> events;
    private String email;
    private List<LocalDate> time;
}
