package com.demo.consumer;

import org.apache.kafka.clients.consumer.ConsumerRecord;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.kafka.annotation.DltHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.demo.schema.ExampleRequest;

@Component
public class ExampleRequestConsumer {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @KafkaListener(topics = "${kafka.topics.request}")
    public void consume(final ConsumerRecord<?, ExampleRequest> message) {
        ExampleRequest exampleRequest = message.value();
        logger.info("ExampleRequestConsumer.handle id={}", exampleRequest.getId());
        if (exampleRequest.getFavoriteNumber() == 999) {
            throw new IllegalStateException("Testing 999");
        }
        logger.info("processed request with id={}", exampleRequest.getId());
    }

    @DltHandler
    public void dlt(final ConsumerRecord<?, Object> message) {
        String className = (message.value() == null) ? "Unknown object" : message.value().getClass().getName();
        logger.error("Dead letter received: topic={} key={} className={}", message.topic(), message.key(), className);
    }
}

