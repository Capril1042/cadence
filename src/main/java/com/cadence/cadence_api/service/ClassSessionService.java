package com.cadence.cadence_api.service;

import com.cadence.cadence_api.model.ClassSession;
import com.cadence.cadence_api.repository.ClassSessionRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClassSessionService {

    private final ClassSessionRepository classSessionRepository;

    public ClassSessionService(ClassSessionRepository classSessionRepository) {
        this.classSessionRepository = classSessionRepository;
    }

    public List<ClassSession> getUpcomingClasses() {
        return classSessionRepository.findAll();
    }
}