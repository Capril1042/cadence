package com.cadence.cadence_api.controller;

import com.cadence.cadence_api.model.AnalyticsEvent;
import com.cadence.cadence_api.service.AnalyticsEventService;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

import java.util.List;

@RestController
@RequestMapping("/api/analytics/events")
@CrossOrigin(origins = "http://localhost:5173")
public class AnalyticsEventController {

    private final AnalyticsEventService analyticsEventService;

    public AnalyticsEventController(
            AnalyticsEventService analyticsEventService
    ) {
        this.analyticsEventService = analyticsEventService;
    }

    @PostMapping
    public AnalyticsEvent trackEvent(
            @RequestParam String eventName,
            @RequestParam(required = false) Long memberId,
            @RequestParam(required = false) Long classSessionId
    ) {
        return analyticsEventService.trackEvent(
                eventName,
                memberId,
                classSessionId
        );
    }

    @GetMapping
    public List<AnalyticsEvent> getAllEvents() {
        return analyticsEventService.getAllEvents();
    }

    @GetMapping("/funnel")
    public Map<String, Object> getBookingFunnel() {
        return analyticsEventService.getBookingFunnel();
    }

    @GetMapping("/funnel/{classSessionId}")
    public Map<String, Object> getClassFunnel(
            @PathVariable Long classSessionId
    ) {
        return analyticsEventService.getClassFunnel(classSessionId);
    }

    @GetMapping("/class-summary")
    public List<Object[]> getClassEventSummary() {
        return analyticsEventService.getClassEventSummary();
    }
}
