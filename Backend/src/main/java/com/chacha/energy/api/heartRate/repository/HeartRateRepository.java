package com.chacha.energy.api.heartRate.repository;

import com.chacha.energy.domain.heartRate.entity.HeartRate;
import com.chacha.energy.domain.heartStatus.entity.HeartStatus;
import com.chacha.energy.domain.member.entity.Member;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface HeartRateRepository extends JpaRepository<Member, Integer> {
    @Query("SELECT hr FROM HeartRate hr WHERE hr.member.id = :memberId ORDER BY hr.createdTime DESC")
    Optional<HeartRate> findLatestByMemberId(@Param("memberId") Integer memberId);

}