package com.nttdata.companytrainingmanagement.services;

import com.nttdata.companytrainingmanagement.entities.*;
import com.nttdata.companytrainingmanagement.repos.EmployeeRepository;
import com.nttdata.companytrainingmanagement.repos.EmployeeTrainingRepository;
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

class EmployeeServiceTest {

    @Mock
    private EmployeeRepository employeeRepository;

    @Mock
    private EmployeeTrainingRepository employeeTrainingRepository;

    @Mock
    private TrainingRepository trainingRepository;

    @InjectMocks
    private EmployeeService employeeService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateEmployee() {
        //Arrange
        Employee employee = new Employee(1L, "Miruna", "Lupas", "mirunalupas21@gmail.com", "Java");

        //Act
        when(employeeRepository.save(employee)).thenReturn(employee);
        Employee result = employeeService.createEmployee(employee);

        //Assert
        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Miruna", result.getFirstName());
        assertEquals("Lupas", result.getLastName());
        assertEquals("mirunalupas21@gmail.com", result.getEmail());
        assertEquals("Java", result.getDepartment());
    }

    @Test
    void testGetEmployeeById() {
        // Arrange
        Employee employee = new Employee(1L, "Miruna", "Lupas", "mirunalupas21@gmail.com", "Java");

        when(employeeRepository.findById(1L)).thenReturn(Optional.of(employee));
        when(employeeRepository.findById(2L)).thenReturn(Optional.empty());

        // Act
        Employee foundEmployee = employeeService.getEmployeeById(1L);
        Employee notFoundEmployee = employeeService.getEmployeeById(2L);

        // Assert
        assertEquals("Miruna", foundEmployee.getFirstName());
        assertNull(notFoundEmployee);
    }

    @Test
    void testUpdateEmployee() {

        //Arrange
        Employee employee = new Employee(1L, "Miruna", "Lupas", "mirunalupas21@gmail.com", "Java");
        Employee updatedEmployee = new Employee(1L, "Miruna-Sabina", "Lupas", "mirunalupas21@gmail.com", "Java");

        //Act
        when(employeeRepository.findById(employee.getId())).thenReturn(java.util.Optional.ofNullable(employee));
        when(employeeRepository.save(employee)).thenReturn(employee);

        Employee result = employeeService.updateEmployee(employee.getId(), updatedEmployee);

        //Assert
        assertNotNull(result);
        assertEquals("Miruna-Sabina", result.getFirstName());
        assertEquals("Lupas", result.getLastName());
        assertEquals("mirunalupas21@gmail.com", result.getEmail());
        assertEquals("Java", result.getDepartment());
    }

    @Test
    void testDeleteEmployee() {

        // Act
        employeeService.deleteEmployee(1L);

        // Assert
        verify(employeeRepository, times(1)).deleteById(1L);
    }

    @Test
    public void testRegisterEmployeeForTraining_Success() {

        //Arrange
        Employee employee = new Employee(1L, "Miruna", "Lupas", "mirunalupas21@gmail.com", "Java");
        Training training = new Training(1L, "Java Training", Platform.UDEMY, 10.5, new Author(1L,"Joseph","White"));

        //Act
        when(employeeRepository.findById(employee.getId())).thenReturn(java.util.Optional.ofNullable(employee));
        when(trainingRepository.findById(training.getId())).thenReturn(java.util.Optional.ofNullable(training));
        when(employeeTrainingRepository.save(any(EmployeeTraining.class))).thenReturn(new EmployeeTraining());

        String result = employeeService.registerEmployeeForTraining(employee.getId(), training.getId());

        //Assert
        assertEquals("Employee was successfully enrolled", result);
    }

    @Test
    public void testGetTrainingsForEmployee() {

        //Arrange
        Employee employee = new Employee(1L, "Miruna", "Lupas", "mirunalupas21@gmail.com", "Java");
        List<Training> trainings = new ArrayList<>();
        Training firstTraining = new Training(1L, "Java Training", Platform.UDEMY, 10.5, new Author(1L,"Joseph","White"));
        Training secondTraining = new Training(2L, "AWS Fundamentals", Platform.ACLOUDGURU, 71.0,new Author(2L,"Nick","Connor"));
        trainings.add(firstTraining);
        trainings.add(secondTraining);

        //Act
        when(employeeRepository.findById(employee.getId())).thenReturn(java.util.Optional.ofNullable(employee));
        when(employeeTrainingRepository.findAllTrainingsByEmployeeId(employee.getId())).thenReturn(trainings);

        List<Training> result = employeeService.getTrainingsForEmployee(employee.getId());

        //Assert
        assertEquals(2, result.size());
        assertTrue(result.contains(firstTraining));
        assertTrue(result.contains(secondTraining));
    }

    @Test
    public void testUpdateTrainingStatus() {

        //Arrange
        Employee employee = new Employee(1L, "Miruna", "Lupas", "mirunalupas21@gmail.com", "Java");
        Training training = new Training(1L, "Java Training", Platform.UDEMY, 10.5, new Author(1L,"Joseph","White"));
        EmployeeTraining employeeTraining = new EmployeeTraining(1L,TrainingStatus.NOT_STARTED,0.0,8.0,employee,training);
        Double completedHours = 5.0;
        Double remainingHours = 3.0;

        when(employeeTrainingRepository.findByEmployeeIdAndTrainingId(anyLong(), anyLong())).thenReturn(employeeTraining);

        employeeService.updateTrainingStatus(employeeTraining, completedHours, remainingHours);

        assertEquals(completedHours, employeeTraining.getCompletedHours());
        assertEquals(remainingHours, employeeTraining.getRemainingHours());
        assertEquals(TrainingStatus.IN_PROGRESS, employeeTraining.getStatus());
    }
}