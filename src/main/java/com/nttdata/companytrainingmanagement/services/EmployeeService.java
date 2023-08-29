package com.nttdata.companytrainingmanagement.services;

import com.nttdata.companytrainingmanagement.entities.*;
import com.nttdata.companytrainingmanagement.repos.EmployeeRepository;
import com.nttdata.companytrainingmanagement.repos.EmployeeTrainingRepository;
import com.nttdata.companytrainingmanagement.repos.TrainingRepository;
import org.apache.kafka.common.errors.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService {

    private EmployeeRepository employeeRepository;
    private TrainingRepository trainingRepository;

    private EmployeeTrainingRepository employeeTrainingRepository;

    @Autowired
    public EmployeeService(EmployeeRepository employeeRepository, TrainingRepository trainingRepository, EmployeeTrainingRepository employeeTrainingRepository) {
        this.employeeRepository = employeeRepository;
        this.trainingRepository = trainingRepository;
        this.employeeTrainingRepository = employeeTrainingRepository;
    }

    @Autowired
    public EmployeeService(EmployeeRepository employeeRepository, TrainingRepository trainingRepository) {
        this.employeeRepository = employeeRepository;
        this.trainingRepository = trainingRepository;
    }

    public Employee createEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }

    public Employee getEmployeeById(Long id) {
        return employeeRepository.findById(id).orElse(null);
    }

    public EmployeeTraining findRecordById(Long id){
        return employeeTrainingRepository.findById(id).orElse(null);
    }
    public Employee updateEmployee(Long id, Employee updatedEmployee) {
        Employee existingEmployee = employeeRepository.findById(id).orElse(null);
        if (existingEmployee != null) {
            existingEmployee.setFirstName(updatedEmployee.getFirstName());
            existingEmployee.setLastName(updatedEmployee.getLastName());
            return employeeRepository.save(existingEmployee);
        }
        return null;
    }

    public void deleteEmployee(Long id) {
        employeeRepository.deleteById(id);
    }

    public String registerEmployeeForTraining(Long employeeId, Long trainingId) {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with id " + employeeId));
        Training training = trainingRepository.findById(trainingId)
                .orElseThrow(() -> new ResourceNotFoundException("Training not found with id " + trainingId));

        if (employee != null && training != null) {
            EmployeeTraining employeeTraining = new EmployeeTraining();
            employeeTraining.setEmployee(employee);
            employeeTraining.setTraining(training);
            employeeTraining.setStatus(TrainingStatus.NOT_STARTED);
            employeeTrainingRepository.save(employeeTraining);
            return "Employee was successfully enrolled";
        }
        else return "Employee is already enrolled for the training";
    }

    public List<Training> getTrainingsForEmployee(Long id) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with id " + id));

        return employeeTrainingRepository.findAllTrainingsByEmployeeId(id);
    }

    public void updateTrainingStatus(EmployeeTraining employeeTraining, Double completedHours, Double remainingHours){
        employeeTraining = employeeTrainingRepository.findByEmployeeIdAndTrainingId(employeeTraining.getEmployee().getId(), employeeTraining.getTraining().getId());
        if(employeeTraining !=null){
            employeeTraining.setCompletedHours(completedHours);
            employeeTraining.setRemainingHours(remainingHours);

            if(completedHours == 0 ){
                employeeTraining.setStatus(TrainingStatus.NOT_STARTED);
            }
            else if(completedHours > 0 && completedHours < employeeTraining.getTraining().getHours()){
                employeeTraining.setStatus(TrainingStatus.IN_PROGRESS);
            }
            else if (completedHours == employeeTraining.getTraining().getHours()){
                employeeTraining.setStatus(TrainingStatus.FINISHED);
            }
            employeeTrainingRepository.save(employeeTraining);
        }

    }
}
