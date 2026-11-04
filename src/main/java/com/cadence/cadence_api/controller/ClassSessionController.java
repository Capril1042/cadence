package com.cadence.cadence_api.controller;

import com.cadence.cadence_api.model.ClassSession;
import com.cadence.cadence_api.service.ClassSessionService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@RestController
@RequestMapping("/api/classes")
@CrossOrigin(origins = "http://localhost:5173")
public class ClassSessionController {

    private final ClassSessionService classSessionService;

    public ClassSessionController(ClassSessionService classSessionService) {
        this.classSessionService = classSessionService;
    }

    @GetMapping
    public List<ClassSession> getUpcomingClasses() {
        return classSessionService.getUpcomingClasses();
    }
    @PostMapping("/{classId}/book")
    public ClassSession bookClass(@PathVariable Long classId) {
        return classSessionService.bookClass(classId);
    }
}