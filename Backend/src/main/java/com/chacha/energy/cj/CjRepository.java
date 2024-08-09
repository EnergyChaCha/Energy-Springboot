package com.chacha.energy.cj;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CjRepository extends JpaRepository<CjEntity, Integer> {

}
