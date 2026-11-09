package com.cadence.cadence_api.config;

import com.cadence.cadence_api.model.Booking;
import com.cadence.cadence_api.model.ClassSession;
import com.cadence.cadence_api.model.Member;
import com.cadence.cadence_api.repository.BookingRepository;
import com.cadence.cadence_api.repository.ClassSessionRepository;
import com.cadence.cadence_api.repository.MemberRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.time.LocalDateTime;

@Configuration
@Profile("retention-test")
public class RetentionTestDataInitializer {

    @Bean
    CommandLineRunner initializeRetentionTestData(
            BookingRepository bookingRepository,
            MemberRepository memberRepository,
            ClassSessionRepository classSessionRepository
    ) {
        return args -> {

            if (bookingRepository.count() > 0) {
                return;
            }

            LocalDateTime now = LocalDateTime.now();

            Member jane = memberRepository.save(new Member(
                    "Jane", "Smith", "jane@example.com",
                    "Premium", "Active"
            ));

            Member alex = memberRepository.save(new Member(
                    "Alex", "Jones", "alex@example.com",
                    "Basic", "Active"
            ));

            Member taylor = memberRepository.save(new Member(
                    "Taylor", "Brown", "taylor@example.com",
                    "Premium", "Active"
            ));

            Member casey = memberRepository.save(new Member(
                    "Casey", "Taylor", "casey@example.com",
                    "Basic", "Active"
            ));

            memberRepository.save(new Member(
                    "Morgan", "Lee", "morgan@example.com",
                    "Basic", "Active"
            ));

            ClassSession yoga = classSessionRepository.save(new ClassSession(
                    "Power Yoga", "Sarah", now.plusHours(2), 20
            ));

            ClassSession hiit = classSessionRepository.save(new ClassSession(
                    "HIIT Training", "Marcus", now.plusHours(3), 15
            ));

            ClassSession spin = classSessionRepository.save(new ClassSession(
                    "Spin", "Jessica", now.plusHours(5), 20
            ));

            // Jane: three bookings across two classes
            bookingRepository.save(new Booking(jane, yoga, now.minusDays(2)));
            bookingRepository.save(new Booking(jane, yoga, now.minusDays(5)));
            bookingRepository.save(new Booking(jane, hiit, now.minusDays(8)));

            // Alex: one booking
            bookingRepository.save(new Booking(alex, spin, now.minusDays(3)));

            // Taylor: historical booking
            bookingRepository.save(new Booking(taylor, yoga, now.minusDays(45)));

            // Jane: one cancelled booking (should not count as active)
            Booking cancelledBooking = new Booking(
                    jane, spin, now.minusDays(10)
            );
            cancelledBooking.setStatus("CANCELLED");
            bookingRepository.save(cancelledBooking);

            Booking caseyCancelledBooking = new Booking(
                    casey, spin, now.minusDays(5)
            );

            caseyCancelledBooking.setStatus("CANCELLED");
            bookingRepository.save(caseyCancelledBooking);
        };


    }
}
