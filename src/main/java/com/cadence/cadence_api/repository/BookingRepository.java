package com.cadence.cadence_api.repository;

import com.cadence.cadence_api.model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingRepository extends JpaRepository<Booking, Long> {

    boolean existsByMemberIdAndClassSessionIdAndStatus(
            Long memberId,
            Long classSessionId,
            String status
    );

    long countByClassSessionIdAndStatus(
            Long classSessionId,
            String status
    );
}