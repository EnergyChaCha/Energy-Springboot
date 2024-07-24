package com.chacha.energy.api.heartRate.repository;

import com.chacha.energy.domain.alert.entity.Alert;
import com.chacha.energy.domain.heartRate.entity.HeartRate;
import com.chacha.energy.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface AlertRepository extends JpaRepository<Alert, Integer> {

    List<Alert> findAllByMember(Member member);

    Optional<Alert> findFirstByMemberAndCreatedTimeBetweenOrderByCreatedTimeDesc(Member member, LocalDateTime start, LocalDateTime end);
}
