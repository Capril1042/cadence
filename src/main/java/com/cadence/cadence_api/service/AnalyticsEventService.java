package com.cadence.cadence_api.service;

import com.cadence.cadence_api.model.AnalyticsEvent;
import com.cadence.cadence_api.repository.AnalyticsEventRepository;
import org.springframework.stereotype.Service;
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
}
