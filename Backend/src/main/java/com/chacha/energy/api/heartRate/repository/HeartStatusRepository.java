package com.chacha.energy.api.heartRate.repository;

import com.chacha.energy.domain.heartStatus.entity.HeartStatus;
import com.chacha.energy.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface HeartStatusRepository extends JpaRepository<HeartStatus, Integer> {
    Optional<HeartStatus> findTopByMemberOrderByCreatedTimeDesc(Member member);
}
