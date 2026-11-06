package com.cadence.cadence_api.repository;

import com.cadence.cadence_api.model.AnalyticsEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

public interface AnalyticsEventRepository
        extends JpaRepository<AnalyticsEvent, Long> {

    long countByEventName(String eventName);
    boolean existsByEventNameAndExperimentIdAndMemberIdAndClassSessionId(
            String eventName,
            Long experimentId,
            Long memberId,
            Long classSessionId
    );
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

    List<AnalyticsEvent> findByEventNameAndExperimentIdAndMemberIdAndClassSessionId(
            String eventName,
            Long experimentId,
            Long memberId,
            Long classSessionId
    );


    @Query(value = """
    SELECT
        c.id AS class_session_id,
        c.name AS class_name,

        COUNT(CASE WHEN e.event_name = 'CLASS_VIEWED' THEN 1 END) AS views,

        COUNT(CASE WHEN e.event_name = 'BOOKING_STARTED' THEN 1 END) AS booking_starts,

        COUNT(CASE WHEN e.event_name = 'BOOKING_COMPLETED' THEN 1 END) AS bookings_completed,

        ROUND(
            100.0 * COUNT(CASE WHEN e.event_name = 'BOOKING_STARTED' THEN 1 END)
            / NULLIF(COUNT(CASE WHEN e.event_name = 'CLASS_VIEWED' THEN 1 END), 0),
            2
        ) AS view_to_start_rate,

        ROUND(
            100.0 * COUNT(CASE WHEN e.event_name = 'BOOKING_COMPLETED' THEN 1 END)
            / NULLIF(COUNT(CASE WHEN e.event_name = 'CLASS_VIEWED' THEN 1 END), 0),
            2
        ) AS view_to_booking_rate,
        
ROUND(
    100.0 * (
        COUNT(CASE WHEN e.event_name = 'BOOKING_STARTED' THEN 1 END)
        - COUNT(CASE WHEN e.event_name = 'BOOKING_COMPLETED' THEN 1 END)
    )
    / NULLIF(
        COUNT(CASE WHEN e.event_name = 'BOOKING_STARTED' THEN 1 END),
        0
    ),
    2
) AS booking_abandonment_rate
    FROM class_session c

    LEFT JOIN analytics_event e
        ON c.id = e.class_session_id

    GROUP BY c.id, c.name

    ORDER BY c.id
    """, nativeQuery = true)
    List<Object[]> getClassEventSummary();

    @Query(value = """
    SELECT
        exposure.experiment_variant AS variant,

        COUNT(DISTINCT exposure.member_id) AS exposed_members,

        COUNT(DISTINCT CASE
            WHEN starts.member_id IS NOT NULL
            THEN exposure.member_id
        END) AS booking_starts,

        COUNT(DISTINCT CASE
            WHEN completions.member_id IS NOT NULL
            THEN exposure.member_id
        END) AS bookings_completed

    FROM analytics_event exposure

    LEFT JOIN analytics_event starts
        ON starts.member_id = exposure.member_id
        AND starts.class_session_id = exposure.class_session_id
        AND starts.event_name = 'BOOKING_STARTED'
        AND starts.timestamp >= exposure.timestamp

    LEFT JOIN analytics_event completions
        ON completions.member_id = exposure.member_id
        AND completions.class_session_id = exposure.class_session_id
        AND completions.event_name = 'BOOKING_COMPLETED'
        AND completions.timestamp >= exposure.timestamp

    WHERE exposure.event_name = 'EXPERIMENT_EXPOSURE'
        AND exposure.experiment_id = :experimentId

    GROUP BY exposure.experiment_variant
    ORDER BY exposure.experiment_variant
    """, nativeQuery = true)
    List<Object[]> getExperimentResults(Long experimentId);

}