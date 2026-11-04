package com.cadence.cadence_api.model;

public record Member(
        Long id,
        String firstName,
        String lastName,
        String email,
        String membershipType,
        String status
) {}