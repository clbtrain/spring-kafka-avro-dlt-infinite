package com.demo.service;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.demo.domain.Example;
import com.demo.schema.ExampleRequest;

@Service
public class ExampleService{
    @Autowired
    NewTopic requestKafkaTopic;

    @Autowired
    KafkaTemplate<String, Object> kafkaTemplate;

    public void requestExample(Example example) {
        ExampleRequest request = ExampleRequest.newBuilder()
                .setId(example.getId())
                .setContent(example.getContent())
                .setFavoriteNumber(example.getFavoriteNumber())
                .build();
        kafkaTemplate.send(requestKafkaTopic.name(), example.getId(), request);
    }

}
