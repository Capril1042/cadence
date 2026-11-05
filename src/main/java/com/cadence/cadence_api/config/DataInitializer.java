package com.cadence.cadence_api.config;

import com.cadence.cadence_api.model.ClassSession;
import com.cadence.cadence_api.model.Member;
import com.cadence.cadence_api.repository.ClassSessionRepository;
import com.cadence.cadence_api.repository.MemberRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner initializeData(
            ClassSessionRepository classSessionRepository,
            MemberRepository memberRepository
    ) {
        return args -> {
            if (classSessionRepository.count() == 0) {
                classSessionRepository.save(new ClassSession(
                        "Power Yoga", "Sarah",
                        LocalDateTime.now().plusHours(2), 20
                ));

                classSessionRepository.save(new ClassSession(
                        "HIIT Training", "Marcus",
                        LocalDateTime.now().plusHours(3), 15
                ));

                classSessionRepository.save(new ClassSession(
                        "Spin", "Jessica",
                        LocalDateTime.now().plusHours(5), 20
                ));
            }

            if (memberRepository.count() == 0) {
                memberRepository.save(new Member(
                        "Jane", "Smith", "jane@example.com",
                        "Premium", "Active"
                ));
            }
        };
    }
}