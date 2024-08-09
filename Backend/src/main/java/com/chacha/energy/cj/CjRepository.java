package com.chacha.energy.cj;

import com.chacha.energy.domain.member.entity.Member;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CjRepository extends JpaRepository<CjEntity, Integer> {
    @Query("SELECT new com.chacha.energy.cj.CjDto$staffListDtoResponse(" +
            "m.name, " +
            "COALESCE(hr.bpm, 0), " +
            "c.step, " +
            "c.distance) " +
            "FROM CjEntity c " +
            "JOIN c.member m " +
            "LEFT JOIN c.bpm hr " +
            "WHERE m.name LIKE CONCAT('%', :name, '%')")
    List<CjDto.staffListDtoResponse> findByMemberNameContaining(@Param("name") String name);
}
