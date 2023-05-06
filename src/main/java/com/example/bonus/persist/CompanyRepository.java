package com.example.bonus.persist;

import com.example.bonus.persist.entity.CompanyEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

// CompanyEntity 받고 이의 type인 Long
@Repository
public interface CompanyRepository extends JpaRepository<CompanyEntity, Long> {
    boolean existsByTicker(String ticker);      // CompanyEntity에서 자동으로 ticker에 해당하는 row를 찾아 boolean값으로 반환

    Optional<CompanyEntity> findByName(String name);        // 회사명으로 회사정보 찾기

    // LIKE 연산
    Page<CompanyEntity> findByNameStartingWithIgnoreCase(String s, Pageable pageable);
}