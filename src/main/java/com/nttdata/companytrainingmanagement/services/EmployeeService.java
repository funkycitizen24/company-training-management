package com.nttdata.companytrainingmanagement.services;

import com.nttdata.companytrainingmanagement.entities.Employee;
import com.nttdata.companytrainingmanagement.entities.Training;
import com.nttdata.companytrainingmanagement.repos.EmployeeRepository;
import com.nttdata.companytrainingmanagement.repos.TrainingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class EmployeeService {

    private EmployeeRepository employeeRepository;
    private TrainingRepository trainingRepository;

    @Autowired
    public EmployeeService(EmployeeRepository employeeRepository, TrainingRepository trainingRepository) {
        this.employeeRepository = employeeRepository;
        this.trainingRepository = trainingRepository;
    }

    public Employee createEmployee(Employee employee) {
        List<Training> trainings = new ArrayList<>();
        if (employee.getTrainings() != null) {
            for (Training training : employee.getTrainings()) {
                Training existingTraining = trainingRepository.findByTitle(training.getTitle());
                if (existingTraining != null) {
                    trainings.add(existingTraining);
                }
            }
        }
        employee.setTrainings(trainings);
        return employeeRepository.save(employee);
    }
}
