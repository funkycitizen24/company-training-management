package com.nttdata.companytrainingmanagement.services;

import com.nttdata.companytrainingmanagement.dtos.TrainingDTO;
import com.nttdata.companytrainingmanagement.entities.Author;
import com.nttdata.companytrainingmanagement.entities.Platform;
import com.nttdata.companytrainingmanagement.entities.Training;
import com.nttdata.companytrainingmanagement.repos.TrainingRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TrainingServiceTest {

    @Mock
    private TrainingRepository trainingRepository;

    @InjectMocks
    private TrainingService trainingService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testFindAllTrainings() {
        // Arrange
        List<Training> trainingList = new ArrayList<>();
        trainingList.add(new Training(1L, "Java Training", Platform.UDEMY, 10, new Author(1L,"Joseph","White")));
        trainingList.add(new Training(2L, "AWS Fundamentals", Platform.ACLOUDGURU, 71,new Author(2L,"Nick","Connor")));

        when(trainingRepository.findAll()).thenReturn(trainingList);

        // Act
        List<TrainingDTO> trainingDTOs = trainingService.findAllTrainings();

        // Assert
        assertEquals(2, trainingDTOs.size());
        assertEquals("Java Training", trainingDTOs.get(0).getTitle());
        assertEquals("AWS Fundamentals", trainingDTOs.get(1).getTitle());
    }

    @Test
    public void testGetTrainingById() {
        // Arrange
        Training training = new Training(1L, "Java Training", Platform.UDEMY, 10, new Author(1L,"Joseph","White"));

        when(trainingRepository.findById(1L)).thenReturn(Optional.of(training));
        when(trainingRepository.findById(2L)).thenReturn(Optional.empty());

        // Act
        Training foundTraining = trainingService.getTrainingById(1L);
        Training notFoundTraining = trainingService.getTrainingById(2L);

        // Assert
        assertEquals("Java Training", foundTraining.getTitle());
        assertNull(notFoundTraining);
    }

    @Test
    public void testCreateTraining() {
        // Arrange
        Training newTraining = new Training(1L, "Java Training", Platform.UDEMY, 10, new Author(1L,"Joseph","White"));

        when(trainingRepository.save(newTraining)).thenReturn(newTraining);

        // Act
        Training createdTraining = trainingService.createTraining(newTraining);

        // Assert
        assertEquals("Java Training", createdTraining.getTitle());
    }

    @Test
    public void testUpdateTraining() {
        // Arrange
        Training existingTraining = new Training(1L, "Java Training", Platform.UDEMY, 10, new Author(1L,"Joseph","White"));
        Training updatedTraining = new Training(1L, "Java Training", Platform.UDEMY, 12, new Author(1L,"Joseph","White"));

        when(trainingRepository.findById(1L)).thenReturn(Optional.of(existingTraining));
        when(trainingRepository.save(existingTraining)).thenReturn(updatedTraining);

        // Act
        Training result = trainingService.updateTraining(1L, updatedTraining);

        // Assert
        assertEquals("Java Training", result.getTitle());
        assertEquals(Platform.UDEMY, result.getPlatform());
        assertEquals(12, result.getHours());
    }

    @Test
    public void testDeleteTraining() {
        // Arrange

        // Act
        trainingService.deleteTraining(1L);

        // Assert
        verify(trainingRepository, times(1)).deleteById(1L);
    }
}