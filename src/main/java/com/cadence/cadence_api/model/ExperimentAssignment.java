
package com.cadence.cadence_api.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(
        uniqueConstraints = {
                @UniqueConstraint(
                        columnNames = {"experiment_id", "member_id"}
                )
        }
)
public class ExperimentAssignment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "experiment_id", nullable = false)
    private Experiment experiment;

    @ManyToOne
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    private String variant;

    private LocalDateTime assignedAt;

    protected ExperimentAssignment() {
    }

    public ExperimentAssignment(
            Experiment experiment,
            Member member,
            String variant
    ) {
        this.experiment = experiment;
        this.member = member;
        this.variant = variant;
        this.assignedAt = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public Experiment getExperiment() {
        return experiment;
    }

    public Member getMember() {
        return member;
    }

    public String getVariant() {
        return variant;
    }

    public LocalDateTime getAssignedAt() {
        return assignedAt;
    }
}
