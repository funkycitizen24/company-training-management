package com.nttdata.companytrainingmanagement.controllers;

import com.nttdata.companytrainingmanagement.entities.Training;
import com.nttdata.companytrainingmanagement.services.TrainingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/trainings")
public class TrainingController {

    private TrainingService trainingService;

    @Autowired
    public TrainingController(TrainingService trainingService) {
        this.trainingService = trainingService;
    }

    @GetMapping("")
    public ResponseEntity<List<Training>> getAllTrainings() {
        List<Training> trainings = trainingService.findAllTrainings();
        return new ResponseEntity<>(trainings, HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<Training> createTraining(@RequestBody Training training) {
        Training savedTraining = trainingService.createTraining(training);
        return new ResponseEntity<>(savedTraining, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Training> updateTraining(@PathVariable Long id, @RequestBody Training training) {
        Training updatedTraining = trainingService.updateTraining(id, training);
        return new ResponseEntity<>(updatedTraining, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> removeTraining(@PathVariable Long id) {
        trainingService.deleteTraining(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/trainings-for-author/{authorId}")
    public ResponseEntity<List<Training>> getTrainingsForAuthor(@PathVariable Long authorId) {
        List<Training> trainings = trainingService.getTrainingsForCertainAuthor(authorId);
        return new ResponseEntity<>(trainings, HttpStatus.OK);
    }

}
