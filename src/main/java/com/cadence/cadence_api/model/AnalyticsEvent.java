package com.cadence.cadence_api.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class AnalyticsEvent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String eventName;

    private Long memberId;

    private Long classSessionId;

    private LocalDateTime timestamp;

    private Long experimentId;

    private String experimentVariant;

    protected AnalyticsEvent() {
    }

    public AnalyticsEvent(
            String eventName,
            Long memberId,
            Long classSessionId
    ) {
        this.eventName = eventName;
        this.memberId = memberId;
        this.classSessionId = classSessionId;
        this.timestamp = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public String getEventName() {
        return eventName;
    }

    public Long getMemberId() {
        return memberId;
    }

    public Long getClassSessionId() {
        return classSessionId;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }
    public Long getExperimentId() {
        return experimentId;
    }

    public String getExperimentVariant() {
        return experimentVariant;
    }
    public AnalyticsEvent(
            String eventName,
            Long memberId,
            Long classSessionId,
            Long experimentId,
            String experimentVariant
    ) {
        this.eventName = eventName;
        this.memberId = memberId;
        this.classSessionId = classSessionId;
        this.experimentId = experimentId;
        this.experimentVariant = experimentVariant;
        this.timestamp = LocalDateTime.now();
    }
}
