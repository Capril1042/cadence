package com.cadence.cadence_api.config;

import com.cadence.cadence_api.model.ClassSession;
import com.cadence.cadence_api.repository.ClassSessionRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner initializeClasses(ClassSessionRepository repository) {
        return args -> {
            if (repository.count() == 0) {
                repository.save(new ClassSession(
                        "Power Yoga",
                        "Sarah",
                        LocalDateTime.now().plusHours(2),
                        20
                ));

                repository.save(new ClassSession(
                        "HIIT Training",
                        "Marcus",
                        LocalDateTime.now().plusHours(3),
                        15
                ));

                repository.save(new ClassSession(
                        "Spin",
                        "Jessica",
                        LocalDateTime.now().plusHours(5),
                        20
                ));
            }
        };
    }
}