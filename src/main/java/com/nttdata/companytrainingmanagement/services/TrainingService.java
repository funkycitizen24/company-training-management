package com.nttdata.companytrainingmanagement.services;

import com.nttdata.companytrainingmanagement.entities.Author;
import com.nttdata.companytrainingmanagement.entities.Training;
import com.nttdata.companytrainingmanagement.repos.TrainingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TrainingService {

    private TrainingRepository trainingRepository;

    @Autowired
    public TrainingService(TrainingRepository trainingRepository) {
        this.trainingRepository = trainingRepository;
    }

    public List<Training> findAllTrainings() {
        List<Training> allTrainings = trainingRepository.findAll();
        List<Training> trainingDTOs = new ArrayList<>();

        for (Training training : allTrainings) {
            Training newTraining = new Training();
            newTraining.setId(training.getId());
            newTraining.setTitle(training.getTitle());
            newTraining.setPlatform(training.getPlatform());
            newTraining.setHours(training.getHours());

            trainingDTOs.add(newTraining);
        }
        return trainingDTOs;
    }

    public Training getTrainingById(Long id) {
        return trainingRepository.findById(id).orElse(null);
    }

    public List<Training> getTrainingsForCertainAuthor(Long id){
        return trainingRepository.findByAuthorId(id);
    }
    public Training createTraining(Training training) {
        return trainingRepository.save(training);
    }

    public Training updateTraining(Long id, Training updatedTraining) {
        Training existingTraining = trainingRepository.findById(id).orElse(null);
        if (existingTraining != null) {
            existingTraining.setTitle(updatedTraining.getTitle());
            existingTraining.setPlatform(updatedTraining.getPlatform());
            existingTraining.setHours(updatedTraining.getHours());
            return trainingRepository.save(existingTraining);
        }
        return null;
    }

    public void deleteTraining(Long id) {
        trainingRepository.deleteById(id);
    }
}
