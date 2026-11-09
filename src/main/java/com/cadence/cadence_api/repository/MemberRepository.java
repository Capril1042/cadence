package com.cadence.cadence_api.repository;

import com.cadence.cadence_api.model.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import com.cadence.cadence_api.model.Booking;

import java.util.List;

public interface MemberRepository extends JpaRepository<Member, Long> {

    @Query("""
        SELECT m
        FROM Member m
        WHERE NOT EXISTS (
            SELECT b.id
            FROM Booking b
            WHERE b.member.id = m.id
        )
    """)
    List<Member> findMembersWhoHaveNeverBooked();

    @Query("""
    SELECT m
    FROM Member m
    WHERE EXISTS (
        SELECT b.id
        FROM Booking b
        WHERE b.member.id = m.id
    )
    AND NOT EXISTS (
        SELECT b.id
        FROM Booking b
        WHERE b.member.id = m.id
          AND b.status = 'BOOKED'
    )
""")
    List<Member> findMembersWithOnlyCancelledBookings();
}