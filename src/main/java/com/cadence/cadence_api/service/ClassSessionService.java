package com.cadence.cadence_api.service;

import com.cadence.cadence_api.model.ClassSession;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ClassSessionService {

    private final List<ClassSession> classes = new ArrayList<>(List.of(
            new ClassSession(1L, "Power Yoga", "Sarah", "9:00 AM", 18, 20),
            new ClassSession(2L, "HIIT Training", "Marcus", "10:30 AM", 12, 15),
            new ClassSession(3L, "Spin", "Jessica", "12:00 PM", 20, 20)
    ));

    public List<ClassSession> getUpcomingClasses() {
        return classes;
    }
    public ClassSession bookClass(Long classId) {

        for (int i = 0; i < classes.size(); i++) {
            ClassSession currentClass = classes.get(i);

            if (currentClass.id().equals(classId)) {

                if (currentClass.bookings() >= currentClass.capacity()) {
                    throw new IllegalStateException("This class is fully booked.");
                }

                ClassSession updatedClass = new ClassSession(
                        currentClass.id(),
                        currentClass.name(),
                        currentClass.instructor(),
                        currentClass.time(),
                        currentClass.bookings() + 1,
                        currentClass.capacity()
                );

                classes.set(i, updatedClass);

                return updatedClass;
            }
        }

        throw new IllegalArgumentException("Class not found.");
    }
}