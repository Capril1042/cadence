package com.cadence.cadence_api.dto;

public record MemberRetention(
        Long id,
        String firstName,
        String lastName,
        String email,
        String membershipType
) {
}