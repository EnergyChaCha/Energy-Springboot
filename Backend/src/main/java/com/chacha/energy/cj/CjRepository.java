package com.chacha.energy.cj;

import com.chacha.energy.domain.member.entity.Member;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface CjRepository extends JpaRepository<CjEntity, Integer> {
    @Query("SELECT new com.chacha.energy.cj.CjDto$staffListDtoResponse(" +
            "m.id, " +
            "m.name, " +
            "c.bpm," +
            "c.step, " +
            "c.distance) " +
            "FROM CjEntity c " +
            "JOIN c.member m " +
            "WHERE m.name LIKE CONCAT('%', :name, '%')" +
            "AND (m.createdTime >= :start AND m.createdTime <= :end)" +
            "AND (:bpm IS NULL OR c.bpm >= :bpm )" +
            "AND (:step IS NULL OR c.step >= :step )" +
            "AND (:distance IS NULL OR c.distance >= :distance )"
    )
    Page<CjDto.staffListDtoResponse> findByMemberNameContaining(@Param("name") String name,
                                                                @Param("bpm") Integer bpm,
                                                                @Param("step") Integer step,
                                                                @Param("distance") Double distance,
                                                                @Param("start") LocalDateTime start,
                                                                @Param("end") LocalDateTime end,
                                                                Pageable pageable);

    @Query("select c from CjEntity c " +
            "where c.member.id = :id " +
            "and (function('DATE', c.createdTime) = function('DATE', :currentDate))"
    )
    CjEntity existsByCurrentDate(@Param("id") Integer id,
                                           @Param("currentDate") LocalDateTime currentDate);
}

