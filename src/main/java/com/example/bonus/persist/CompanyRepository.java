package com.example.bonus.persist;

import com.example.bonus.persist.entity.CompanyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

// CompanyEntity 받고 이의 type인 Long
@Repository
public interface CompanyRepository extends JpaRepository<CompanyEntity, Long> {
    boolean existsByTicker(String ticker);      // CompanyEntity에서 자동으로 ticker에 해당하는 row를 찾아 boolean값으로 반환
}