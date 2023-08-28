package com.nttdata.companytrainingmanagement.entities;

import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class TrainingEvent {
    private Long employeeId;
    private Long trainingId;
    private String event;
    private LocalDateTime timestamp;

}