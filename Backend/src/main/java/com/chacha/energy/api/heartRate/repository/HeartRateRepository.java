package com.chacha.energy.api.heartRate.repository;

import com.chacha.energy.api.heartRate.dto.HeartRateDto;
import com.chacha.energy.api.heartRate.dto.ResponseHeartRateDto;
import com.chacha.energy.domain.heartRate.entity.HeartRate;
import com.chacha.energy.domain.heartStatus.entity.HeartStatus;
import com.chacha.energy.domain.member.entity.Member;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface HeartRateRepository extends JpaRepository<HeartRate, Integer> {
//    @Query("SELECT hr FROM HeartRate hr WHERE hr.member.id = :memberId ORDER BY hr.createdTime DESC")
//    Optional<HeartRate> findLatestByMemberId(@Param("memberId") Integer memberId);

    @Query(value = """
            SELECT h.bpm FROM heart_rate h WHERE h.member_id = :memberId AND h.created_time >= CURRENT_TIMESTAMP - INTERVAL '5 minutes' AND h.created_time <= CURRENT_TIMESTAMP ORDER BY h.created_time DESC limit 1""",  nativeQuery = true)
    Optional<Integer> findLatestBpmWithin5Minutes(@Param("memberId") Integer memberId);


    @Query(value =
            "SELECT new com.chacha.energy.api.heartRate.dto.ResponseHeartRateDto(" +
                    "cast(hb.bucket as text), hb.minBpm, hb.maxBpm, hb.avgBpm) " +
                    "FROM (" +
                    "SELECT time_bucket('1 hour', h.createdTime) bucket, " +
                    "MIN(h.bpm) minBpm, MAX(h.bpm) maxBpm, AVG(h.bpm) avgBpm " +
                    "FROM HeartRate h " +
                    "WHERE (h.createdTime BETWEEN :start AND :end) AND (h.member.id) = :id " +
                    "GROUP BY bucket " +
                    "ORDER BY bucket" +
                    ") hb " +
                    "ORDER BY hb.bucket"
    )
    List<ResponseHeartRateDto> findHeartRateStatistics(
            @Param("id") Integer id,
            @Param("start") LocalDateTime start,
            @Param("end") LocalDateTime end);


//    @Query("SELECT new com.chacha.energy.api.heartRate.dto.HeartRateDto.GetHeartRateListDto(" +
//            "m.id, m.name, m.phone, m.loginId, " +
//            "MIN(h.bpm), MAX(h.bpm), ROUND(AVG(h.bpm)), " +
//            "m.minBpmThreshold, m.maxBpmThreshold, " +
//            "CASE " +
//            "   WHEN AVG(h.bpm) > m.maxBpmThreshold THEN 'emergency' " +
//            "   WHEN AVG(h.bpm) < m.minBpmThreshold THEN 'emergency' " +
//            "   WHEN AVG(h.bpm) > m.maxBpmThreshold - 10 THEN 'caution' " +
//            "   WHEN AVG(h.bpm) < m.minBpmThreshold + 10 THEN 'caution' " +
//            "   ELSE 'stable' " +
//            "END) " +
//            "FROM HeartRate h JOIN h.member m " +
//            "WHERE h.createdTime BETWEEN :start AND :end " +
//            "AND (:heartrateStatus IS NULL OR :heartrateStatus = " +
//            "    CASE " +
//            "       WHEN AVG(h.bpm) > m.maxBpmThreshold THEN 'emergency' " +
//            "       WHEN AVG(h.bpm) < m.minBpmThreshold THEN 'emergency' " +
//            "       WHEN AVG(h.bpm) > m.maxBpmThreshold - 10 THEN 'caution' " +
//            "       WHEN AVG(h.bpm) < m.minBpmThreshold + 10 THEN 'caution' " +
//            "       ELSE 'stable' " +
//            "    END) " +
//            "AND (:loginId IS NULL OR m.loginId = :loginId) " +
//            "GROUP BY m.id, m.name, m.phone, m.loginId, m.minBpmThreshold, m.maxBpmThreshold")
//    List<HeartRateDto.GetHeartRateListDto> findAllHeartRateThresholds(
//            @Param("start") LocalDateTime start,
//            @Param("end") LocalDateTime end,
//            @Param("heartrateStatus") String heartrateStatus,
//            @Param("loginId") String loginId);
}