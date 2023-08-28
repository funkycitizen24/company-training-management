package com.nttdata.companytrainingmanagement.controllers;

import com.nttdata.companytrainingmanagement.entities.TrainingEvent;
import com.nttdata.companytrainingmanagement.services.KafkaProducerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/trainingevent")
public class TrainingEventController {
    private KafkaProducerService kafkaProducerService;

    @Autowired
    public TrainingEventController(KafkaProducerService kafkaProducerService) {
        this.kafkaProducerService = kafkaProducerService;
    }

    // Endpoint to add a training event
    @PostMapping("")
    public ResponseEntity<String> addTrainingEvent(@RequestBody TrainingEvent trainingEvent) {
        String message = trainingEvent.toString();
        kafkaProducerService.sendTrainingEvent(message);
        return ResponseEntity.status(HttpStatus.CREATED).body("Training event sent to Kafka");
    }

    @GetMapping("/all")
    public ResponseEntity<List<String>> getAllTrainingEvents() {
        // retrieve training events from Kafka
        List<String> trainingEvents = new ArrayList<>();
        return ResponseEntity.ok(trainingEvents);
    }
}
