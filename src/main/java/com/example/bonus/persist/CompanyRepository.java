package com.example.bonus.persist;

import com.example.bonus.persist.entity.CompanyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

// CompanyEntity 받고 이의 type인 Long
@Repository
public interface CompanyRepository extends JpaRepository<CompanyEntity, Long> {

}
