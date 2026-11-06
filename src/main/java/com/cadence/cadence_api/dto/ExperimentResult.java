package com.cadence.cadence_api.dto;

public record ExperimentResult(
        String variant,
        long exposedMembers,
        long bookingStarts,
        long bookingsCompleted,
        double startConversionRate,
        double completionConversionRate
) {
}