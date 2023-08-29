package com.nttdata.companytrainingmanagement.repos;

import com.nttdata.companytrainingmanagement.entities.Employee;
import com.nttdata.companytrainingmanagement.entities.EmployeeTraining;
import com.nttdata.companytrainingmanagement.entities.Training;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface EmployeeTrainingRepository extends JpaRepository<EmployeeTraining, Long> {

    @Query("SELECT et.training FROM EmployeeTraining et WHERE et.employee.id =: employeeId")
    List<Training> findAllTrainingsByEmployeeId(Long employeeId);

    @Query("SELECT et.training FROM EmployeeTraining et WHERE et.employee.id = :employeeId AND et.training.id =: trainingId")
    EmployeeTraining findByEmployeeIdAndTrainingId(Long employeeId, Long trainingId);
}
