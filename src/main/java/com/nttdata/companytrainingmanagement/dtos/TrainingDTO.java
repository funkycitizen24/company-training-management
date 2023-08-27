package com.nttdata.companytrainingmanagement.dtos;


import com.nttdata.companytrainingmanagement.entities.Platform;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class TrainingDTO {
    private Long id;
    private String title;
    private Platform platform;
    private double hours;

    // Constructors, getters, and setters
}
