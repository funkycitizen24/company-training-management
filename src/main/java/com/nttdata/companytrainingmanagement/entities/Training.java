package com.nttdata.companytrainingmanagement.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "TRAININGS")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Training {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Platform platform;

    @Column(nullable = false)
    private Integer hours;

    @ManyToOne
    @JoinColumn(name = "author_id",nullable = false)
    private Author author;
}
