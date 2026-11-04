package com.cadence.cadence_api.model;

public record ClassSession(
        Long id,
        String name,
        String instructor,
        String time,
        int bookings,
        int capacity
) {}
