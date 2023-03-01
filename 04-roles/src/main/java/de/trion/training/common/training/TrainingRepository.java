package de.trion.training.common.training;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TrainingRepository extends JpaRepository<Training, String> {
   @Query("SELECT t.location FROM Training t")
   List<String> findLocations();
}
