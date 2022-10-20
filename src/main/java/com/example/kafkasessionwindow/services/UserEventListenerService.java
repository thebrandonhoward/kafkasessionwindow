package com.example.kafkasessionwindow.services;

import com.example.kafkasessionwindow.bindings.UserEventListenerBinding;
import com.example.kafkasessionwindow.models.UserEvent;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.SessionWindows;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.time.ZoneOffset;

@EnableBinding(UserEventListenerBinding.class)
@Service
@Slf4j
public class UserEventListenerService {
    @StreamListener("user-event-input-channel")
    public void process(KStream<String, UserEvent> input) {
        input.peek((k,v) -> log.info("Key = " + k + " Create Time = "
            + Instant.ofEpochMilli(v.getCreatedTime()).atOffset(ZoneOffset.UTC)))
                .groupByKey()
                .windowedBy(SessionWindows.with(Duration.ofMinutes(5)))
                .count()
                .toStream()
                .foreach((k,v) -> log.info(
                        "UserID: " + k.key() +
                                " Window start: " +
                                Instant.ofEpochMilli(k.window().start())
                                        .atOffset(ZoneOffset.UTC) +
                                " Window end: " +
                                Instant.ofEpochMilli(k.window().end())
                                        .atOffset(ZoneOffset.UTC) +
                                " Count: " + v +
                                " Window#: " + k.window().hashCode()));
    }
}
