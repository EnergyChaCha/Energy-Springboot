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

    @Query(value = """
            SELECT h.bpm FROM heart_rate h WHERE h.member_id = :memberId AND h.created_time >= CURRENT_TIMESTAMP - INTERVAL '5 minutes' AND h.created_time <= CURRENT_TIMESTAMP ORDER BY h.created_time DESC limit 1""",  nativeQuery = true)
    Optional<Integer> findLatestBpmWithin5Minutes(@Param("memberId") Integer memberId);
}