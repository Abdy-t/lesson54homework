package com.example.lesson54homework.util;



import com.example.lesson54homework.model.Event;
import com.example.lesson54homework.model.Subscribe;
import com.example.lesson54homework.repository.EventRepository;
import com.example.lesson54homework.repository.SubscribeRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.IntStream;

@Configuration
public class PreloadDatabaseWithData {

    @Bean
    CommandLineRunner initDatabase(SubscribeRepository subscribeRepository, EventRepository eventRepository) {
        return(args) -> {
            List<Subscribe> subscribes = readSubscribes("subscribes.json");
            List<Event> events = readEvents("events.json");
            IntStream.range(0, events.size()).forEachOrdered(i -> events.get(i).setTime(between()));
            subscribeRepository.deleteAll();
            eventRepository.deleteAll();
            subscribeRepository.saveAll(subscribes);
            eventRepository.saveAll(events);
        };
    }

    private static List<Subscribe> readSubscribes(String fileName) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            var data = Files.readString(Paths.get(fileName));
            var listType = new TypeReference<List<Subscribe>>(){};
            return mapper.readValue(data, listType);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return List.of();
    }
    private static List<Event> readEvents(String fileName) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            var data = Files.readString(Paths.get(fileName));
            var listType = new TypeReference<List<Event>>(){};
            return mapper.readValue(data, listType);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return List.of();
    }

    public static LocalDate between() {
        LocalDate startInclusive = LocalDate.now().minusDays(10);
        LocalDate endExclusive = startInclusive.plusDays(30);
        long startEpochDay = startInclusive.toEpochDay();
        long endEpochDay = endExclusive.toEpochDay();
        long randomDay = ThreadLocalRandom
                .current()
                .nextLong(startEpochDay, endEpochDay);
        return LocalDate.ofEpochDay(randomDay);
    }
}
