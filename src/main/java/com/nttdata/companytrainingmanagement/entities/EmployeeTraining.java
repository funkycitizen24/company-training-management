package com.nttdata.companytrainingmanagement.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "EMPLOYEE_TRAINING")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class EmployeeTraining {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(value = EnumType.STRING)
    private TrainingStatus status;

    private Double completedHours;
    private Double remainingHours;

    @ManyToOne
    @JoinColumn(name = "employee_id")
    private Employee employee;

    @ManyToOne
    @JoinColumn(name = "training_id")
    private Training training;


}
