package com.cadence.cadence_api.repository;

import com.cadence.cadence_api.model.ExperimentAssignment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ExperimentAssignmentRepository
        extends JpaRepository<ExperimentAssignment, Long> {

    Optional<ExperimentAssignment> findByExperimentIdAndMemberId(
            Long experimentId,
            Long memberId
    );
}
