package com.example.bonus.persist;

import com.example.bonus.persist.entity.DividendEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;

// DividendEntity를 받고 이의 type인 Long
@Repository
public interface DividendRepository extends JpaRepository<DividendEntity, Long> {
    List<DividendEntity> findAllByCompanyId(Long companyId);

    @Transactional
    void deleteAllByCompanyId(Long id);

    // unique key로 존재여부 확인
    boolean existsByCompanyIdAndDate(Long companyId, LocalDateTime date);
}
