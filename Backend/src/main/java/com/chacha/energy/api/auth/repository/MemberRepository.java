package com.chacha.energy.api.auth.repository;

import com.chacha.energy.domain.member.entity.Member;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Integer> {

    Optional<Member> findByLoginId(String loginId);

    boolean existsByLoginId(String loginId);

    @Query("select m from Member m WHERE m.name = :name")
    List<Member> findAllByName(@Param("name") String name);

    @Query("select m from Member m WHERE m.name = :name AND m.workArea = :workArea")
    List<Member> findAllByNameAndWorkArea(@Param("name") String name, @Param("workArea") String workArea);

    List<Member> findAllByRole(String role);

}
