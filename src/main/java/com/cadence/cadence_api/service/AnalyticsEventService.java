package com.cadence.cadence_api.service;

import com.cadence.cadence_api.model.AnalyticsEvent;
import com.cadence.cadence_api.repository.AnalyticsEventRepository;
import org.springframework.stereotype.Service;
import com.cadence.cadence_api.dto.ExperimentResult;
import java.util.Map;

import java.util.List;

@Service
public class AnalyticsEventService {

    private final AnalyticsEventRepository analyticsEventRepository;

    public AnalyticsEventService(
            AnalyticsEventRepository analyticsEventRepository
    ) {
        this.analyticsEventRepository = analyticsEventRepository;
    }

    public AnalyticsEvent trackEvent(
            String eventName,
            Long memberId,
            Long classSessionId
    ) {
        AnalyticsEvent event = new AnalyticsEvent(
                eventName,
                memberId,
                classSessionId
        );

        return analyticsEventRepository.save(event);
    }
    public AnalyticsEvent trackEvent(
            String eventName,
            Long memberId,
            Long classSessionId,
            Long experimentId,
            String experimentVariant
    ) {
        if ("EXPERIMENT_EXPOSURE".equals(eventName)) {
            List<AnalyticsEvent> existingEvents =
                    analyticsEventRepository
                            .findByEventNameAndExperimentIdAndMemberIdAndClassSessionId(
                                    eventName,
                                    experimentId,
                                    memberId,
                                    classSessionId
                            );

            if (!existingEvents.isEmpty()) {
                return existingEvents.get(0);
            }
        }

        AnalyticsEvent event = new AnalyticsEvent(
                eventName,
                memberId,
                classSessionId,
                experimentId,
                experimentVariant
        );

        return analyticsEventRepository.save(event);
    }
    public List<AnalyticsEvent> getAllEvents() {
        return analyticsEventRepository.findAll();
    }

    public Map<String, Object> getBookingFunnel() {

        long classViews =
                analyticsEventRepository.countByEventName("CLASS_VIEWED");

        long bookingStarts =
                analyticsEventRepository.countByEventName("BOOKING_STARTED");

        long bookingCompletions =
                analyticsEventRepository.countByEventName("BOOKING_COMPLETED");

        double viewToStartRate = classViews == 0
                ? 0
                : (double) bookingStarts / classViews * 100;

        double startToCompleteRate = bookingStarts == 0
                ? 0
                : (double) bookingCompletions / bookingStarts * 100;

        double overallConversionRate = classViews == 0
                ? 0
                : (double) bookingCompletions / classViews * 100;

        return Map.of(
                "classViews", classViews,
                "bookingStarts", bookingStarts,
                "bookingCompletions", bookingCompletions,
                "viewToStartRate", viewToStartRate,
                "startToCompleteRate", startToCompleteRate,
                "overallConversionRate", overallConversionRate
        );
    }

    public Map<String, Object> getClassFunnel(Long classSessionId) {

        long classViews =
                analyticsEventRepository.countByEventNameAndClassSessionId(
                        "CLASS_VIEWED", classSessionId
                );

        long bookingStarts =
                analyticsEventRepository.countByEventNameAndClassSessionId(
                        "BOOKING_STARTED", classSessionId
                );

        long bookingCompletions =
                analyticsEventRepository.countByEventNameAndClassSessionId(
                        "BOOKING_COMPLETED", classSessionId
                );

        double viewToStartRate = classViews == 0
                ? 0
                : (double) bookingStarts / classViews * 100;

        double startToCompleteRate = bookingStarts == 0
                ? 0
                : (double) bookingCompletions / bookingStarts * 100;

        double overallConversionRate = classViews == 0
                ? 0
                : (double) bookingCompletions / classViews * 100;

        return Map.of(
                "classSessionId", classSessionId,
                "classViews", classViews,
                "bookingStarts", bookingStarts,
                "bookingCompletions", bookingCompletions,
                "viewToStartRate", viewToStartRate,
                "startToCompleteRate", startToCompleteRate,
                "overallConversionRate", overallConversionRate
        );
    }
    public List<Object[]> getClassEventSummary() {
        return analyticsEventRepository.getClassEventSummary();
    }

    public List<ExperimentResult> getExperimentResults(Long experimentId) {
        List<Object[]> rows =
                analyticsEventRepository.getExperimentResults(experimentId);

        return rows.stream()
                .map(row -> {
                    long exposedMembers = ((Number) row[1]).longValue();
                    long bookingStarts = ((Number) row[2]).longValue();
                    long bookingsCompleted = ((Number) row[3]).longValue();

                    double startRate = exposedMembers == 0
                            ? 0.0
                            : (double) bookingStarts / exposedMembers * 100;

                    double completionRate = exposedMembers == 0
                            ? 0.0
                            : (double) bookingsCompleted / exposedMembers * 100;

                    return new ExperimentResult(
                            (String) row[0],
                            exposedMembers,
                            bookingStarts,
                            bookingsCompleted,
                            startRate,
                            completionRate
                    );
                })
                .toList();
    }
}
