package com.chacha.energy.api.heartRate.repository;

import com.chacha.energy.api.heartRate.dto.HeartRateDto;
import com.chacha.energy.api.heartRate.dto.ResponseHeartRateAvg;
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
    @Query("SELECT new com.chacha.energy.api.heartRate.dto.ResponseHeartRateAvg(" +
            "m.minBpmThreshold, " +
            "m.maxBpmThreshold, " +
            "AVG(h.bpm)) " +
            "FROM HeartRate h " +
            "JOIN h.member m " +
            "WHERE m.id = :memberId " +
            "AND h.createdTime BETWEEN :start AND :end " +
            "GROUP BY m.id, m.minBpmThreshold, m.maxBpmThreshold")
    List<ResponseHeartRateAvg> findHeartRateStatistics(
            @Param("memberId") Integer memberId,
            @Param("start") LocalDateTime start,
            @Param("end") LocalDateTime end);

    // HI-02
    // SQL 바꾼 부분: group by 부분에 heartrate 부분을 빼고, select 문에서 heartrate 관련 부분은 집계함수 사용
    //     이유: 우리가 구해야 하는 것은 '사람 별' 심박수 데이터인데, group by에 heartrate 관련 내용이 들어가면 '심박수 별' 사람 데이터가 나옴
    // DTO 바꾼 부분: wrapper 클래스 적용 (DB에서 불러와서 dto에 바로 넣으려면 primitive는 안 되고 Wrapper 클래스만 가능. null 값이 들어올 수도 있어서 그런듯)
    // TODO: dto에 bpm에 대한 min 값, average 값, max 값 추가 (wrapper 클래스 이용)
    // TODO: heartrateStatus 구하기
    //       전제조건: sql로 하려면 집계함수를 적용시켜야함
    //       방안1: 그냥 heartrateStatus는 sql로 구하지 않고 자바 코드로 계산하기
    //       방안2: HeartRate 엔티티에서 status 타입을 Integer로 바꾸고,
    //             DB 저장할 때는 숫자로 저장: HeartStatus 2(emergency) 1(caution) 0(statble)
    //             그리고 sql에서 불러올 때 max(h.heartStatus)로 하면 그 기간동안의 가장 높은 등급이 조회됨
    @Query("SELECT new com.chacha.energy.api.heartRate.dto.ResponseListHeartRateDto(" +
            "m.id, m.name, m.phone, m.loginId, " +
            "min(h.bpm), max(h.bpm), avg(h.bpm), "+
            "m.minBpmThreshold, m.maxBpmThreshold, max(h.heartStatus)) " +
            "FROM HeartRate h " +
            "LEFT JOIN Member m ON h.member.id =  m.id " +
            "WHERE (h.createdTime BETWEEN :start AND :end) " +
            "AND (:loginId IS NULL OR m.loginId = :loginId) " +
            "AND (:heartrateStatus IS NULL OR h.heartStatus = :heartrateStatus)" +
            "GROUP BY m.id, m.name, m.phone, m.loginId, m.minBpmThreshold, m.maxBpmThreshold" )
    List<ResponseListHeartRateDto> findAllHeartRateThresholds(
            @Param("start") LocalDateTime start,
            @Param("end") LocalDateTime end,
            @Param("heartrateStatus") Integer heartrateStatus,
            @Param("loginId") String loginId);

}
