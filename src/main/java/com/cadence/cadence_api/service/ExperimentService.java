
package com.cadence.cadence_api.service;

import com.cadence.cadence_api.model.Experiment;
import com.cadence.cadence_api.model.ExperimentAssignment;
import com.cadence.cadence_api.model.Member;
import com.cadence.cadence_api.repository.ExperimentAssignmentRepository;
import com.cadence.cadence_api.repository.ExperimentRepository;
import com.cadence.cadence_api.repository.MemberRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.ThreadLocalRandom;

@Service
public class ExperimentService {

    private final ExperimentRepository experimentRepository;
    private final ExperimentAssignmentRepository assignmentRepository;
    private final MemberRepository memberRepository;

    public ExperimentService(
            ExperimentRepository experimentRepository,
            ExperimentAssignmentRepository assignmentRepository,
            MemberRepository memberRepository
    ) {
        this.experimentRepository = experimentRepository;
        this.assignmentRepository = assignmentRepository;
        this.memberRepository = memberRepository;
    }

    @Transactional
    public ExperimentAssignment assignVariant(
            Long experimentId,
            Long memberId
    ) {
        // Reuse an existing assignment if one already exists
        return assignmentRepository
                .findByExperimentIdAndMemberId(experimentId, memberId)
                .orElseGet(() -> createAssignment(experimentId, memberId));
    }

    private ExperimentAssignment createAssignment(
            Long experimentId,
            Long memberId
    ) {
        Experiment experiment = experimentRepository.findById(experimentId)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Experiment not found"
                ));

        if (!"RUNNING".equals(experiment.getStatus())) {
            throw new IllegalStateException(
                    "Experiment is not currently running"
            );
        }

        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Member not found"
                ));

        // Randomly assign the member to variant A or B
        String variant = ThreadLocalRandom.current().nextBoolean()
                ? "A"
                : "B";

        ExperimentAssignment assignment =
                new ExperimentAssignment(experiment, member, variant);

        return assignmentRepository.save(assignment);
    }

    public Experiment createExperiment(String name, String description) {
        Experiment experiment = new Experiment(
                name,
                description,
                "DRAFT"
        );

        return experimentRepository.save(experiment);
    }

    public Experiment startExperiment(Long experimentId) {
        Experiment experiment = experimentRepository.findById(experimentId)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Experiment not found"
                ));

        if (!"DRAFT".equals(experiment.getStatus())) {
            throw new IllegalStateException(
                    "Only draft experiments can be started"
            );
        }

        experiment.setStatus("RUNNING");

        return experimentRepository.save(experiment);
    }
}

