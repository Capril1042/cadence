package com.cadence.cadence_api.service;

import com.cadence.cadence_api.model.Booking;
import com.cadence.cadence_api.model.ClassSession;
import com.cadence.cadence_api.model.Member;
import com.cadence.cadence_api.repository.BookingRepository;
import com.cadence.cadence_api.repository.ClassSessionRepository;
import com.cadence.cadence_api.repository.MemberRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class BookingService {

    private final BookingRepository bookingRepository;
    private final MemberRepository memberRepository;
    private final ClassSessionRepository classSessionRepository;

    public BookingService(
            BookingRepository bookingRepository,
            MemberRepository memberRepository,
            ClassSessionRepository classSessionRepository
    ) {
        this.bookingRepository = bookingRepository;
        this.memberRepository = memberRepository;
        this.classSessionRepository = classSessionRepository;
    }

    @Transactional
    public Booking bookClass(Long memberId, Long classSessionId) {

        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("Member not found."));

        ClassSession classSession = classSessionRepository.findById(classSessionId)
                .orElseThrow(() -> new IllegalArgumentException("Class not found."));

        boolean alreadyBooked =
                bookingRepository.existsByMemberIdAndClassSessionIdAndStatus(
                        memberId,
                        classSessionId,
                        "BOOKED"
                );

        if (alreadyBooked) {
            throw new IllegalStateException("You have already booked this class.");
        }

        long currentBookings =
                bookingRepository.countByClassSessionIdAndStatus(
                        classSessionId,
                        "BOOKED"
                );

        if (currentBookings >= classSession.getCapacity()) {
            throw new IllegalStateException("This class is fully booked.");
        }

        Booking booking = new Booking(member, classSession);

        return bookingRepository.save(booking);
    }

    @Transactional
    public Booking cancelBooking(Long bookingId) {

        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new IllegalArgumentException("Booking not found."));

        if ("CANCELLED".equals(booking.getStatus())) {
            throw new IllegalStateException("This booking is already cancelled.");
        }

        booking.setStatus("CANCELLED");

        return bookingRepository.save(booking);
    }

    public List<Booking> getAllBookings() {
        return bookingRepository.findAll();
    }
}