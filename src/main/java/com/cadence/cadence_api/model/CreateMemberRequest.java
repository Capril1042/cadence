package com.cadence.cadence_api.model;

public record CreateMemberRequest(
        String firstName,
        String lastName,
        String email,
        String membershipType
) {}
