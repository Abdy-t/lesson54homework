package com.example.lesson54homework.dto;

import com.example.lesson54homework.model.Event;
import lombok.*;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

@Data
@Builder(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true)
public class EventDTO {

    public static EventDTO from(Event event) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
//        String strDate = dateFormat.format(event.getTime());
        String strDate = event.getTime().toString();

        return builder()
                .id(event.getId())
                .time(strDate)
                .name(event.getName())
                .description(event.getDescription())
                .build();
    }

    private String id;
    private String time;
    private String name;
    private String description;
}
