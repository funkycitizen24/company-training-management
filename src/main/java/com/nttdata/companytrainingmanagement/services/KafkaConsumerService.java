package com.nttdata.companytrainingmanagement.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumerService {

    private final String topicName;

    public KafkaConsumerService(@Value("${kafka.topic.name}") String topicName) {
        this.topicName = topicName;
        System.out.println("KafkaConsumerService initialized with topic: " + topicName);
    }

    @KafkaListener(topics = "${kafka.topic.name}", groupId = "my-group")
    public void listen(String message) {
        // Process the training event received from Kafka
        // You can log it, save it to a database, or perform any other necessary actions
        System.out.println("Received training event: " + message);
    }
}

