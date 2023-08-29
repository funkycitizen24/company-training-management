package com.nttdata.companytrainingmanagement.controllers;

import com.fasterxml.jackson.databind.deser.std.StringDeserializer;
import com.nttdata.companytrainingmanagement.config.KafkaConfig;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;
import java.util.*;

@RestController
@RequestMapping("/training-event")
public class TrainingEventController {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    private ConsumerFactory<String, String> consumerFactory;

    @Autowired
    private KafkaConfig kafkaConfig;

    @PostMapping("/add")
    public void addTrainingEvent(@RequestBody String trainingEvent) {
        String topic = kafkaConfig.getDefaultTopic();

        kafkaTemplate.send(topic, trainingEvent);
    }


    @GetMapping("/read")
    public List<String> readTrainingEvents() {

        String topic = kafkaConfig.getDefaultTopic();

        // Kafka consumer configuration
        Map<String, Object> consumerProps = new HashMap<>();
        consumerProps.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaConfig.getBootstrapServers());
        consumerProps.put(ConsumerConfig.GROUP_ID_CONFIG, "training-consumer-group");
        consumerProps.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        consumerProps.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());

        // Create a Kafka consumer
        KafkaConsumer<String, String> kafkaConsumer = new KafkaConsumer<>(consumerProps);

        kafkaConsumer.subscribe(Collections.singletonList(topic));

        List<String> trainingEvents = new ArrayList<>();
        kafkaConsumer.poll(Duration.ofMillis(100));
        kafkaConsumer.seekToBeginning(kafkaConsumer.assignment());
        kafkaConsumer.poll(Duration.ofMillis(100)).forEach(record -> {
            trainingEvents.add(record.value());
        });

        kafkaConsumer.close();

        return trainingEvents;
    }

}
