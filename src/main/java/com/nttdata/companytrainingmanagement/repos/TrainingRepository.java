package com.nttdata.companytrainingmanagement.repos;

import com.nttdata.companytrainingmanagement.entities.Author;
import com.nttdata.companytrainingmanagement.entities.Training;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TrainingRepository extends JpaRepository<Training, Long> {
    List<Training> findByAuthor(Author author);

    Training findByTitle(String title);
}
