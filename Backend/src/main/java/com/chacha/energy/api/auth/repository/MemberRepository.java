package com.chacha.energy.api.auth.repository;

import com.chacha.energy.api.auth.dto.AuthDto;
import com.chacha.energy.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Integer> {

    Optional<Member> findByLoginId(String loginId);
    boolean existsByLoginId(String loginId);

}
