package com.chacha.energy.api.auth.repository;

import com.chacha.energy.api.auth.dto.AuthDto;
import com.chacha.energy.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
<<<<<<< Updated upstream
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Integer> {

    Optional<Member> findByLoginId(String loginId);
    boolean existsByLoginId(String loginId);
=======

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Integer> {

    Member toEntity(AuthDto.SignUpRequest authDto);

    Optional<Member> findByLoginId(String loginId);
>>>>>>> Stashed changes
}
