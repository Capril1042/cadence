package com.cadence.cadence_api.service;

import com.cadence.cadence_api.repository.BookingRepository;
import org.springframework.stereotype.Service;
import com.cadence.cadence_api.model.Member;
import com.cadence.cadence_api.repository.MemberRepository;
import com.cadence.cadence_api.dto.MemberRetention;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
public class RetentionService {

    private final BookingRepository bookingRepository;
    private final MemberRepository memberRepository;

    public RetentionService(
            BookingRepository bookingRepository,
            MemberRepository memberRepository
    ) {
        this.bookingRepository = bookingRepository;
        this.memberRepository = memberRepository;
    }


    public Map<String, Object> getOverallRetention() {

        long totalBookers =
                bookingRepository.countDistinctBookedMembers();

        long returningBookers =
                bookingRepository.findReturningMemberIds().size();

        long oneTimeBookers = totalBookers - returningBookers;

        double repeatBookingRate = totalBookers == 0
                ? 0.0
                : (double) returningBookers / totalBookers * 100;

        return Map.of(
                "totalBookers", totalBookers,
                "returningBookers", returningBookers,
                "oneTimeBookers", oneTimeBookers,
                "repeatBookingRate", repeatBookingRate
        );
    }

    public List<Map<String, Object>> getClassRetention() {

        List<Object[]> rows =
                bookingRepository.findSameClassRepeatBookingCounts();

        return rows.stream()
                .map(row -> Map.<String, Object>of(
                        "classSessionId", row[0],
                        "className", row[1],
                        "returningMembers", row[2]
                ))
                .toList();
    }

    public List<MemberRetention> getInactiveMembers() {

        LocalDateTime cutoffDate = LocalDateTime.now().minusDays(30);

        List<Long> inactiveMemberIds =
                bookingRepository.findInactiveMemberIds(cutoffDate);

        return inactiveMemberIds.stream()
                .map(memberId -> memberRepository.findById(memberId)
                        .orElseThrow(() ->
                                new IllegalStateException("Member not found: " + memberId)
                        )
                )
                .map(member -> new MemberRetention(
                        member.getId(),
                        member.getFirstName(),
                        member.getLastName(),
                        member.getEmail(),
                        member.getMembershipType()
                ))
                .toList();
    }

    public List<MemberRetention> getNeverBookedMembers() {
        return memberRepository.findMembersWhoHaveNeverBooked()
                .stream()
                .map(member -> new MemberRetention(
                        member.getId(),
                        member.getFirstName(),
                        member.getLastName(),
                        member.getEmail(),
                        member.getMembershipType()
                ))
                .toList();
    }

    public List<MemberRetention> getCancelledOnlyMembers() {
        return memberRepository.findMembersWithOnlyCancelledBookings()
                .stream()
                .map(member -> new MemberRetention(
                        member.getId(),
                        member.getFirstName(),
                        member.getLastName(),
                        member.getEmail(),
                        member.getMembershipType()
                ))
                .toList();
    }


}
