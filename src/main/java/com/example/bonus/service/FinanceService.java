package com.example.bonus.service;

import com.example.bonus.exception.impl.NoCompanyException;
import com.example.bonus.model.Company;
import com.example.bonus.model.Dividend;
import com.example.bonus.model.ScrapedResult;
import com.example.bonus.model.constants.CacheKey;
import com.example.bonus.persist.CompanyRepository;
import com.example.bonus.persist.DividendRepository;
import com.example.bonus.persist.entity.CompanyEntity;
import com.example.bonus.persist.entity.DividendEntity;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@AllArgsConstructor
public class FinanceService {

    private final CompanyRepository companyRepository;
    private final DividendRepository dividendRepository;

    @Cacheable(key = "#companyName", value = CacheKey.KEY_FINANCE)
    // 이 메서드는 요청이 자주 들어오고, data가 자주 변경되므로 캐싱
    public ScrapedResult getDividendByCompanyName(String companyName) {
        log.info("search company -> " + companyName);

        // 1. 회사명을 기준으로 회사정보 조회
        CompanyEntity companyEntity = this.companyRepository.findByName(companyName)
                .orElseThrow(() -> new NoCompanyException());

        // 2. 조회된 회사 id로 배당금 조회
        List<DividendEntity> dividendEntities = this.dividendRepository
                .findAllByCompanyId(companyEntity.getId());

        // 3. 결과 반환

        List<Dividend> dividends = dividendEntities.stream()
                                .map(e -> new Dividend(e.getDate(), e.getDividend()))
                                .collect(Collectors.toList());

        return new ScrapedResult(new Company(companyEntity.getTicker(), companyEntity.getName()),
                                dividends);
    }
}
