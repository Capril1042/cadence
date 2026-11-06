package com.cadence.cadence_api.repository;

import com.cadence.cadence_api.model.Experiment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExperimentRepository extends JpaRepository<Experiment, Long> {
}
