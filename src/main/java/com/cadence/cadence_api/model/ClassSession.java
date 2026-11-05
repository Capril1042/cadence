package com.cadence.cadence_api.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.time.LocalDateTime;

@Entity
public class ClassSession {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String instructor;
    private LocalDateTime time;
    private int capacity;

    protected ClassSession() {
    }

    public ClassSession(
            String name,
            String instructor,
            LocalDateTime time,
            int capacity
    ) {
        this.name = name;
        this.instructor = instructor;
        this.time = time;
        this.capacity = capacity;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getInstructor() {
        return instructor;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public int getCapacity() {
        return capacity;
    }
}
