package com.cadence.cadence_api.repository;

import com.cadence.cadence_api.model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

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

    @Query("""
    SELECT b.member.id
    FROM Booking b
    WHERE b.status = 'BOOKED'
    GROUP BY b.member.id
    HAVING COUNT(b.id) >= 2
""")
    List<Long> findReturningMemberIds();

    @Query("""
    SELECT COUNT(DISTINCT b.member.id)
    FROM Booking b
    WHERE b.status = 'BOOKED'
""")
    long countDistinctBookedMembers();

    @Query("""
    SELECT b.classSession.id,
           b.classSession.name,
           COUNT(DISTINCT b.member.id)
    FROM Booking b
    WHERE b.status = 'BOOKED'
      AND b.member.id IN (
          SELECT b2.member.id
          FROM Booking b2
          WHERE b2.status = 'BOOKED'
            AND b2.classSession.id = b.classSession.id
          GROUP BY b2.member.id
          HAVING COUNT(b2.id) >= 2
      )
    GROUP BY b.classSession.id, b.classSession.name
""")
    List<Object[]> findSameClassRepeatBookingCounts();

    @Query("""
    SELECT b.member.id
    FROM Booking b
    WHERE b.status = 'BOOKED'
    GROUP BY b.member.id
    HAVING MAX(b.bookingDate) < :cutoffDate
""")
    List<Long> findInactiveMemberIds(
            @Param("cutoffDate") LocalDateTime cutoffDate
    );
}