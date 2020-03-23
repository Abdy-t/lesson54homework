package com.example.lesson54homework.dto;

import com.example.lesson54homework.model.Event;
import com.example.lesson54homework.model.Subscribe;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

@Data
@Builder(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true)
public class SubscribeDTO {

    public static SubscribeDTO from(Subscribe subscribe) {
        List<String> eventsList = new ArrayList<>();
        if (subscribe.getEvents() == null){
            eventsList.add("no events");
        } else {
            IntStream.range(0, subscribe.getEvents().size()).forEachOrdered(i -> {
                eventsList.add(subscribe.getEvents().get(i).getId());
            });
        }
        List<String> timeList = new ArrayList<>();

        if (subscribe.getTime() == null){
            eventsList.add("no registration");
        } else {
            IntStream.range(0, subscribe.getTime().size()).forEachOrdered(i -> {
                timeList.add( subscribe.getEvents().get(i).getId() + "|" + subscribe.getTime().get(i).toString());
            });
        }
        return builder()
                .id(subscribe.getId())
                .events(eventsList)
                .email(subscribe.getEmail())
                .time(timeList)
                .build();
    }

    private String id;
    private List<String> events;
    private String email;
    private List<String> time;
}
