package com.nttdata.companytrainingmanagement.services;

import com.nttdata.companytrainingmanagement.dtos.TrainingDTO;
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

    public List<TrainingDTO> findAllTrainings() {
        List<Training> allTrainings = trainingRepository.findAll();
        List<TrainingDTO> trainingDTOs = new ArrayList<>();

        for (Training training : allTrainings) {
            TrainingDTO dto = new TrainingDTO();
            dto.setId(training.getId());
            dto.setTitle(training.getTitle());
            dto.setPlatform(training.getPlatform());
            dto.setHours(training.getHours());

            trainingDTOs.add(dto);
        }
        return trainingDTOs;
    }

    public Training getTrainingById(Long id) {
        return trainingRepository.findById(id).orElse(null);
    }

    public Training findTrainingByTitle(String title){
        return  trainingRepository.findByTitle(title);
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
