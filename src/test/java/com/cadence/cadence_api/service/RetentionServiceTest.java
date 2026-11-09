package com.cadence.cadence_api.service;

import com.cadence.cadence_api.repository.BookingRepository;
import com.cadence.cadence_api.repository.MemberRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import com.cadence.cadence_api.dto.MemberRetention;
import com.cadence.cadence_api.model.Member;

import java.lang.reflect.Field;
import java.util.Optional;
import static org.mockito.ArgumentMatchers.any;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RetentionServiceTest {

    @Mock
    private BookingRepository bookingRepository;

    @Mock
    private MemberRepository memberRepository;

    private RetentionService retentionService;

    @BeforeEach
    void setUp() {
        retentionService = new RetentionService(
                bookingRepository,
                memberRepository
        );
    }

    @Test
    void shouldCalculateOverallRetention() {
        when(bookingRepository.countDistinctBookedMembers())
                .thenReturn(10L);

        when(bookingRepository.findReturningMemberIds())
                .thenReturn(List.of(1L, 2L, 3L, 4L));

        var result = retentionService.getOverallRetention();

        assertEquals(10L, result.get("totalBookers"));
        assertEquals(4L, result.get("returningBookers"));
        assertEquals(6L, result.get("oneTimeBookers"));
        assertEquals(40.0, result.get("repeatBookingRate"));
    }

    @Test
    void shouldReturnZeroPercentWhenThereAreNoBookers() {
        when(bookingRepository.countDistinctBookedMembers())
                .thenReturn(0L);

        when(bookingRepository.findReturningMemberIds())
                .thenReturn(List.of());

        var result = retentionService.getOverallRetention();

        assertEquals(0L, result.get("totalBookers"));
        assertEquals(0L, result.get("returningBookers"));
        assertEquals(0L, result.get("oneTimeBookers"));
        assertEquals(0.0, result.get("repeatBookingRate"));
    }

    @Test
    void shouldReturnClassRetentionCounts() {
        List<Object[]> rows = List.<Object[]>of(
                new Object[]{1L, "Power Yoga", 3L},
                new Object[]{2L, "Spin", 2L}
        );

        when(bookingRepository.findSameClassRepeatBookingCounts())
                .thenReturn(rows);

        var result = retentionService.getClassRetention();

        assertEquals(2, result.size());

        assertEquals(1L, result.get(0).get("classSessionId"));
        assertEquals("Power Yoga", result.get(0).get("className"));
        assertEquals(3L, result.get(0).get("returningMembers"));

        assertEquals(2L, result.get(1).get("classSessionId"));
        assertEquals("Spin", result.get(1).get("className"));
        assertEquals(2L, result.get(1).get("returningMembers"));
    }

    @Test
    void shouldReturnInactiveMembersAsDtos() throws Exception {
        Member member = new Member(
                "Taylor",
                "Brown",
                "taylor@example.com",
                "STANDARD",
                "ACTIVE"
        );
        Field idField = Member.class.getDeclaredField("id");
        idField.setAccessible(true);
        idField.set(member, 3L);

        when(bookingRepository.findInactiveMemberIds(any()))
                .thenReturn(List.of(3L));

        when(memberRepository.findById(3L))
                .thenReturn(Optional.of(member));

        List<MemberRetention> result = retentionService.getInactiveMembers();

        assertEquals(1, result.size());
        assertEquals("Taylor", result.get(0).firstName());
        assertEquals("Brown", result.get(0).lastName());
        assertEquals("taylor@example.com", result.get(0).email());
        assertEquals("STANDARD", result.get(0).membershipType());
    }

    @Test
    void shouldReturnNeverBookedMembersAsDtos() {
        Member member = new Member(
                "Morgan",
                "Lee",
                "morgan@example.com",
                "STANDARD",
                "ACTIVE"
        );

        when(memberRepository.findMembersWhoHaveNeverBooked())
                .thenReturn(List.of(member));

        List<MemberRetention> result =
                retentionService.getNeverBookedMembers();

        assertEquals(1, result.size());
        assertEquals("Morgan", result.get(0).firstName());
        assertEquals("Lee", result.get(0).lastName());
        assertEquals("morgan@example.com", result.get(0).email());
    }

    @Test
    void shouldReturnCancelledOnlyMembersAsDtos() {
        Member member = new Member(
                "Casey",
                "Taylor",
                "casey@example.com",
                "STANDARD",
                "ACTIVE"
        );

        when(memberRepository.findMembersWithOnlyCancelledBookings())
                .thenReturn(List.of(member));

        List<MemberRetention> result =
                retentionService.getCancelledOnlyMembers();

        assertEquals(1, result.size());
        assertEquals("Casey", result.get(0).firstName());
        assertEquals("Taylor", result.get(0).lastName());
        assertEquals("casey@example.com", result.get(0).email());
    }
}