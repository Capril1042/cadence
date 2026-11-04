package com.cadence.cadence_api.repository;

import com.cadence.cadence_api.model.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
}