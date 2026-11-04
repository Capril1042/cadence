package com.cadence.cadence_api.controller;

import com.cadence.cadence_api.model.CreateMemberRequest;
import com.cadence.cadence_api.model.Member;
import com.cadence.cadence_api.service.MemberService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/members")
@CrossOrigin(origins = "http://localhost:5173")
public class MemberController {

    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping
    public List<Member> getAllMembers() {
        return memberService.getAllMembers();
    }

    @PostMapping
    public Member addMember(@RequestBody CreateMemberRequest request) {
        return memberService.addMember(
                request.firstName(),
                request.lastName(),
                request.email(),
                request.membershipType()
        );
    }
}