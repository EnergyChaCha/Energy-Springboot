package com.chacha.energy.cj.repository;

import com.chacha.energy.cj.entity.ActivityMetric;
import com.chacha.energy.cj.dto.ActivityMetricDto;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ActivityMetricRepository extends JpaRepository<ActivityMetric, Integer> {
    @Query("SELECT new com.chacha.energy.cj.dto.ActivityMetricDto$staffListDtoResponse(" +
            "m.id, " +
            "m.name, " +
            "c.originBpm," +
            "c.step, " +
            "c.distance) " +
            "FROM ActivityMetric c " +
            "JOIN c.member m " +
            "WHERE m.name LIKE CONCAT('%', :name, '%')" +
            "AND (:bpm IS NULL OR c.originBpm >= :bpm )" +
            "AND (:step IS NULL OR c.step >= :step )" +
            "AND (:distance IS NULL OR c.distance >= :distance )"
    )
    Page<ActivityMetricDto.staffListDtoResponse> findByMemberNameContaining(@Param("name") String name,
                                                                            @Param("bpm") Integer bpm,
                                                                            @Param("step") Integer step,
                                                                            @Param("distance") Double distance,
                                                                            Pageable pageable);

    @Query("select c from ActivityMetric c " +
            "where c.member.id = :id " +
            "and (function('DATE', c.createdTime) = function('DATE', :currentDate))"
    )
    ActivityMetric existsByCurrentDate(@Param("id") Integer id,
                                       @Param("currentDate") LocalDateTime currentDate);

    List<ActivityMetric> findAllByBpmIsNull();
}

