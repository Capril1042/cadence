package com.cadence.cadence_api.repository;

import com.cadence.cadence_api.model.AnalyticsEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface AnalyticsEventRepository
        extends JpaRepository<AnalyticsEvent, Long> {

    long countByEventName(String eventName);

    @Query("""
        SELECT COUNT(e)
        FROM AnalyticsEvent e
        WHERE e.eventName = :eventName
        AND e.classSessionId = :classSessionId
    """)
    long countByEventNameAndClassSessionId(
            String eventName,
            Long classSessionId
    );
}