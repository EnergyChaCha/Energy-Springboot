package com.chacha.energy.api.heartRate.repository;

import com.chacha.energy.api.heartRate.dto.HeartRateDto;
import com.chacha.energy.api.heartRate.dto.ResponseHeartRateDto;
import com.chacha.energy.api.heartRate.dto.ResponseListHeartRateDto;
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


    // HI-01

    @Query(value =
            "SELECT " +
                    "cast(hb.bucket as text) as date, hb.minBpm as minimumBpm, hb.maxBpm as maximumBpm, hb.avgBpm as averageBpm " +
                    "FROM (" +
                    "SELECT time_bucket(cast((:itv || ' hour') as interval), h.created_time) bucket, " +
                    "MIN(h.bpm) minBpm, MAX(h.bpm) maxBpm, AVG(h.bpm) avgBpm " +
                    "FROM heart_rate h " +
                    "WHERE (h.created_time BETWEEN :start AND :end) AND (h.member_id) = :id " +
                    "GROUP BY bucket " +
                    "ORDER BY bucket" +
                    ") hb " +
                    "ORDER BY hb.bucket",
            nativeQuery = true
    )
    List<ResponseHeartRateDto> findHeartRateStatistics(
            @Param("id") Integer id,
            @Param("start") LocalDateTime start,
            @Param("end") LocalDateTime end,
            @Param("itv") String itv);


    // HI-04
//    @Query("SELECT new com.chacha.energy.api.heartRate.dto.HeartRateDto.GetHeartRateAvg(" +
//            "MIN(h.bpm), MAX(h.bpm), AVG(h.bpm)) " +
//            "FROM HeartRate h " +
//            "WHERE h.member.id = :memberId " +
//            "AND h.createdTime BETWEEN :start AND :end")
//    HeartRateDto.GetHeartRateAvg findHeartRateStatistics(
//            @Param("memberId") Long memberId,
//            @Param("start") LocalDateTime start,
//            @Param("end") LocalDateTime end);

    // HI-02
    @Query("SELECT new com.chacha.energy.api.heartRate.dto.ResponseListHeartRateDto(" +
            "h.id, m.name, m.phone, m.loginId, " +
            "h.bpm, (m.minBpmThreshold + m.maxBpmThreshold)/2, "+
            "m.minBpmThreshold, m.maxBpmThreshold, h.heartStatus) " +
            "FROM HeartRate h " +
            "LEFT JOIN Member m ON h.member.id =  m.id " +
            "WHERE (h.createdTime BETWEEN :start AND :end) " +
            "AND (:loginId IS NULL OR m.loginId = :loginId) " +
            "AND (:heartrateStatus IS NULL OR h.heartStatus = :heartrateStatus)" +
            "GROUP BY h.id, m.name, m.phone, m.loginId, m.minBpmThreshold, m.maxBpmThreshold, h.heartStatus" )
    List<ResponseListHeartRateDto> findAllHeartRateThresholds(
            @Param("start") LocalDateTime start,
            @Param("end") LocalDateTime end,
            @Param("heartrateStatus") String heartrateStatus,
            @Param("loginId") String loginId);
}
