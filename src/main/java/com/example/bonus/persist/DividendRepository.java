package com.example.bonus.persist;

import com.example.bonus.persist.entity.DividendEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

// DividendEntity를 받고 이의 type인 Long
@Repository
public interface DividendRepository extends JpaRepository<DividendEntity, Long> {

}
