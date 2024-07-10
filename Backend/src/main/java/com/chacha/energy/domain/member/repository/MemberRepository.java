package com.chacha.energy.domain.member.repository;

import com.chacha.energy.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Integer> {
}
