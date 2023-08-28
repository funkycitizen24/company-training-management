package com.nttdata.companytrainingmanagement.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaProducerService {

    private KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    public KafkaProducerService(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
        System.out.println("KafkaProducerService initialized with topic: " + topicName);
    }

    @Value("${kafka.topic.name}")
    private String topicName;

    public void sendTrainingEvent(String message) {
        kafkaTemplate.send(topicName, message);
    }


}
