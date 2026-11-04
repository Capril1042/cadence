package com.cadence.cadence_api.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;
    private String lastName;
    private String email;
    private String membershipType;
    private String status;

    // JPA requires a no-argument constructor
    protected Member() {
    }

    public Member(
            String firstName,
            String lastName,
            String email,
            String membershipType,
            String status
    ) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.membershipType = membershipType;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getMembershipType() {
        return membershipType;
    }

    public String getStatus() {
        return status;
    }
}