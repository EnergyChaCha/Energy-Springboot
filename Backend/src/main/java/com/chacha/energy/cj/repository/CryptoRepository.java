package com.chacha.energy.cj.repository;

import com.chacha.energy.cj.dto.ActivityMetricDto;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import jakarta.persistence.Query;

import java.util.List;
import java.util.stream.Collectors;

@Repository
public class CryptoRepository {

    @PersistenceContext
    private EntityManager entityManager;

    public String encrypt(String key, String data) {
        Query query = entityManager.createNativeQuery(
                "SELECT encode(encrypt(convert_to(:data, 'utf8'), convert_to(:key, 'utf8'), 'aes'), 'hex')"
        );
        query.setParameter("key", key);
        query.setParameter("data", data);
        return (String) query.getSingleResult();
    }

    public String decrypt(String key, String data) {
        Query query = entityManager.createNativeQuery(
                "SELECT convert_from(decrypt(decode(:data, 'hex'), convert_to(:key, 'utf8'), 'aes'), 'utf8')"
        );
        query.setParameter("key", key);
        query.setParameter("data", data);
        return (String) query.getSingleResult();
    }

    public String encryptAllBpm(String key) {
        Query query = entityManager.createNativeQuery(
                "update cj " +
                    "set bpm=encode(encrypt(convert_to(CAST(origin_bpm AS varchar),'utf8'), convert_to(:key, 'utf8'),'aes'),'hex') " +
                    "where cj.bpm is null"
        );
        query.setParameter("key", key);
        query.executeUpdate();

        return "완료";
    }

    public Page<ActivityMetricDto.staffListDtoResponse> findByMemberNameContaining(
            String name, Integer bpm, Integer step, Double distance, String key, Pageable pageable) {

        // 기본 쿼리
        String baseQuery = "SELECT m.id, m.name, CAST(convert_from(decrypt(decode(c.bpm, 'hex'), convert_to(:key, 'utf8'), 'aes'), 'utf8') AS INTEGER) AS decrypted_bpm, c.step, c.distance " +
                "FROM cj c " +
                "JOIN member m ON c.member_id = m.id " +
                "WHERE m.name LIKE CONCAT('%', :name, '%') ";

        String filterCluase = "";
        if (bpm != null) {
            filterCluase +="AND CAST(convert_from(decrypt(decode(c.bpm, 'hex'), convert_to(:key, 'utf8'), 'aes'), 'utf8') AS INTEGER) >= :bpm ";
        }
        if (step != null) {
            filterCluase +="AND c.step >= :step ";
        }
        if (distance != null) {
            filterCluase +="AND c.distance >= :distance ";
        }

        // 다중 정렬 조건을 반영한 ORDER BY 절 추가
        String sortClause = pageable.getSort().stream()
                .map(order -> " " + order.getProperty() + " " + order.getDirection().name())
                .collect(Collectors.joining(", "));

        if (!filterCluase.isEmpty()) {
            baseQuery += filterCluase;
        }

        if (!sortClause.isEmpty()) {
            baseQuery += " ORDER BY" + sortClause;
        }

        // 쿼리 실행
        Query query = entityManager.createNativeQuery(baseQuery);
        query.setParameter("key", key);
        query.setParameter("name", name);

        if (bpm != null) {
            query.setParameter("bpm", bpm);
        }
        if (step != null) {
            query.setParameter("step", step);
        }
        if (distance != null) {
            query.setParameter("distance", distance);
        }

        // 페이징 처리
        query.setFirstResult((int) pageable.getOffset());
        query.setMaxResults(pageable.getPageSize());

        // 결과 매핑 및 DTO 변환
        List<Object[]> results = query.getResultList();
        List<ActivityMetricDto.staffListDtoResponse> dtoList = results.stream()
                .map(row -> new ActivityMetricDto.staffListDtoResponse(
                        ((Number) row[0]).intValue(),   // m.id
                        (String) row[1],                 // m.name
                        (Integer) row[2],                // c.bpm decrypt
                        (Integer) row[3],                // c.step
                        (Double) row[4]                  // c.distance
                ))
                .collect(Collectors.toList());

        // 전체 레코드 수를 위한 count 쿼리
        String countQuery = "SELECT COUNT(*) " +
                "FROM cj c " +
                "JOIN member m ON c.member_id = m.id " +
                "WHERE m.name LIKE CONCAT('%', :name, '%') ";

        countQuery += filterCluase;
        Query countQueryObj = entityManager.createNativeQuery(countQuery);
        countQueryObj.setParameter("name", name);

        if (bpm != null) {
            countQueryObj.setParameter("bpm", bpm);
            countQueryObj.setParameter("key", key);
        }
        if (step != null) {
            countQueryObj.setParameter("step", step);
        }
        if (distance != null) {
            countQueryObj.setParameter("distance", distance);
        }

        Long total = ((Number) countQueryObj.getSingleResult()).longValue();

        return new PageImpl<>(dtoList, pageable, total);
    }
}
