package com.example.kafkasessionwindow.bindings;

import org.apache.kafka.streams.kstream.KStream;
import org.springframework.cloud.stream.annotation.Input;

public interface UserEventListenerBinding {
    @Input("user-event-input-channel")
    KStream<String,String> inputStream();
}
