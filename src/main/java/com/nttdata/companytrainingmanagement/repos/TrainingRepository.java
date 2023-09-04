package com.nttdata.companytrainingmanagement.repos;

import com.nttdata.companytrainingmanagement.entities.Training;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TrainingRepository extends JpaRepository<Training, Long> {
    List<Training> findByAuthorId(Long id);
}
