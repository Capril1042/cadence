package com.cadence.cadence_api.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Member member;

    @ManyToOne
    private ClassSession classSession;

    private LocalDateTime bookingDate;

    private String status;

    protected Booking() {
    }

    public Booking(Member member, ClassSession classSession) {
        this.member = member;
        this.classSession = classSession;
        this.bookingDate = LocalDateTime.now();
        this.status = "BOOKED";
    }

    public Long getId() {
        return id;
    }

    public Member getMember() {
        return member;
    }

    public ClassSession getClassSession() {
        return classSession;
    }

    public LocalDateTime getBookingDate() {
        return bookingDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
