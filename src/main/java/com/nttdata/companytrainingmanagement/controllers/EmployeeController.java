package com.nttdata.companytrainingmanagement.controllers;

import com.nttdata.companytrainingmanagement.entities.Employee;
import com.nttdata.companytrainingmanagement.entities.EmployeeTraining;
import com.nttdata.companytrainingmanagement.entities.Training;
import com.nttdata.companytrainingmanagement.services.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    private EmployeeService employeeService;

    @Autowired
    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping("/{employeeId}/trainings")
    public List<Training> getAllTrainingsForEmployee(@PathVariable Long employeeId) {
        return employeeService.getTrainingsForEmployee(employeeId);
    }

    @PostMapping
    public ResponseEntity<Employee> createEmployee(@RequestBody Employee employee) {
        Employee createdEmployee = employeeService.createEmployee(employee);
        return new ResponseEntity<>(createdEmployee, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Employee> updateEmployee(@PathVariable Long id, @RequestBody Employee updatedEmployee){
        Employee employee = employeeService.updateEmployee(id, updatedEmployee);
        if (employee != null) {
            return ResponseEntity.ok(employee);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteEmployee(@PathVariable Long id) {
        employeeService.deleteEmployee(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{employeeId}/register-for-training/{trainingId}")
    public ResponseEntity<String> registerEmployeeForTraining(
            @PathVariable Long employeeId,
            @PathVariable Long trainingId
    ) {
        String message = employeeService.registerEmployeeForTraining(employeeId, trainingId);

        if ("Successfully enrolled".equals(message)) {
            return ResponseEntity.status(HttpStatus.CREATED).body(message);
        } else if ("Is already enrolled".equals(message)) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(message);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
        }
    }

    @PatchMapping("/{employeeTrainingId}/update-status")
    public ResponseEntity<String> updateTrainingStatus(
            @PathVariable Long employeeTrainingId,
            @RequestParam Double completedHours,
            @RequestParam Double remainingHours) {

        EmployeeTraining employeeTraining = employeeService.findRecordById(employeeTrainingId);

        if (employeeTraining != null) {
            employeeService.updateTrainingStatus(employeeTraining, completedHours, remainingHours);
            return ResponseEntity.status(HttpStatus.OK).body("Training status updated successfully.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("EmployeeTraining not found.");
        }
    }
}
