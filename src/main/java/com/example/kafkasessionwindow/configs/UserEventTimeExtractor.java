package com.example.kafkasessionwindow.configs;

import com.example.kafkasessionwindow.models.UserEvent;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.streams.processor.TimestampExtractor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
@Slf4j
public class UserEventTimeExtractor implements TimestampExtractor {

    @Override
    public long extract(ConsumerRecord<Object, Object> consumerRecord, long l) {
        UserEvent userEvent = (UserEvent) consumerRecord.value();

        log.info("Event time: {}", userEvent.getCreatedTime());

        return (userEvent.getCreatedTime() > 0) ? userEvent.getCreatedTime() : l;
    }

    @Bean("userEvenExt")
    @Primary
    public TimestampExtractor userEventTimeExtractor() {
        return new UserEventTimeExtractor();
    }
}
