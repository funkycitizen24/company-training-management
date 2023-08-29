package com.nttdata.companytrainingmanagement.services;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class KafkaMessageService implements MessageService {

    private final KafkaTemplate<Integer, String> kafkaTemplate;
    private final ConsumerFactory<Integer, String> consumerFactory;

    public KafkaMessageService(KafkaTemplate<Integer, String> kafkaTemplate,
                               ConsumerFactory<Integer, String> consumerFactory) {
        this.kafkaTemplate = kafkaTemplate;
        this.consumerFactory = consumerFactory;
    }

    @Override
    public void sendMessage(String topic, String message) {
        kafkaTemplate.send(topic, message);
    }

    @Override
    public List<String> readMessages(String topic) {
        return null;
    }
}
