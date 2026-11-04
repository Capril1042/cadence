package com.cadence.cadence_api.service;

import com.cadence.cadence_api.model.Member;
import com.cadence.cadence_api.repository.MemberRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MemberService {

    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public List<Member> getAllMembers() {
        return memberRepository.findAll();
    }

    public Member addMember(
            String firstName,
            String lastName,
            String email,
            String membershipType
    ) {
        Member member = new Member(
                firstName,
                lastName,
                email,
                membershipType,
                "Active"
        );

        return memberRepository.save(member);
    }
}