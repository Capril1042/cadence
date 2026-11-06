package com.cadence.cadence_api.controller;

import com.cadence.cadence_api.model.ExperimentAssignment;
import com.cadence.cadence_api.service.ExperimentService;
import org.springframework.web.bind.annotation.*;
import com.cadence.cadence_api.model.Experiment;

@RestController
@RequestMapping("/api/experiments")
@CrossOrigin(origins = "http://localhost:5173")
public class ExperimentController {

    private final ExperimentService experimentService;

    public ExperimentController(ExperimentService experimentService) {
        this.experimentService = experimentService;
    }

    @PostMapping("/{experimentId}/assign")
    public ExperimentAssignment assignVariant(
            @PathVariable Long experimentId,
            @RequestParam Long memberId
    ) {
        return experimentService.assignVariant(experimentId, memberId);
    }

    @PostMapping
    public Experiment createExperiment(
            @RequestParam String name,
            @RequestParam String description
    ) {
        return experimentService.createExperiment(name, description);
    }

    @PostMapping("/{experimentId}/start")
    public Experiment startExperiment(
            @PathVariable Long experimentId
    ) {
        return experimentService.startExperiment(experimentId);
    }
}
