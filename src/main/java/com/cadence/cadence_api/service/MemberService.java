package com.cadence.cadence_api.service;

import com.cadence.cadence_api.model.Member;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MemberService {

    private final List<Member> members = new ArrayList<>(List.of(
            new Member(1L, "Alex", "Morgan", "alex@example.com", "Monthly", "Active"),
            new Member(2L, "Jordan", "Lee", "jordan@example.com", "Annual", "Active"),
            new Member(3L, "Taylor", "Brooks", "taylor@example.com", "Monthly", "Inactive")
    ));

    public List<Member> getAllMembers() {
        return members;
    }

    public Member addMember(String firstName, String lastName, String email, String membershipType) {
        long nextId = members.stream()
                .mapToLong(Member::id)
                .max()
                .orElse(0L) + 1;

        Member member = new Member(
                nextId,
                firstName,
                lastName,
                email,
                membershipType,
                "Active"
        );

        members.add(member);
        return member;
    }
}
