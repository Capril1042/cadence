package com.cadence.cadence_api.controller;

import com.cadence.cadence_api.dto.ExperimentResult;
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
            @RequestParam(required = false) Long classSessionId,
            @RequestParam(required = false) Long experimentId,
            @RequestParam(required = false) String variant
    ) {
        if (experimentId != null || variant != null) {
            return analyticsEventService.trackEvent(
                    eventName,
                    memberId,
                    classSessionId,
                    experimentId,
                    variant
            );
        }

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
    @PostMapping("/exposure")
    public AnalyticsEvent trackExperimentExposure(
            @RequestParam Long experimentId,
            @RequestParam Long memberId,
            @RequestParam Long classSessionId,
            @RequestParam String variant
    ) {
        return analyticsEventService.trackEvent(
                "EXPERIMENT_EXPOSURE",
                memberId,
                classSessionId,
                experimentId,
                variant
        );
    }
    @GetMapping("/experiments/{experimentId}/results")
    public List<ExperimentResult> getExperimentResults(
            @PathVariable Long experimentId
    ) {
        return analyticsEventService.getExperimentResults(experimentId);
    }

}
