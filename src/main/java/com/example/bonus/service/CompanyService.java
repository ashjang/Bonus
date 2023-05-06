package com.example.bonus.service;

import com.example.bonus.model.Company;
import com.example.bonus.model.ScrapedResult;
import com.example.bonus.persist.CompanyRepository;
import com.example.bonus.persist.DividendRepository;
import com.example.bonus.persist.entity.CompanyEntity;
import com.example.bonus.persist.entity.DividendEntity;
import com.example.bonus.scraper.Scraper;
import lombok.AllArgsConstructor;
import org.apache.commons.collections4.Trie;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CompanyService {

    private final Trie trie;            // AppConfig.java에서 미리 설정해줌
    private final Scraper yahooFinanceScraper;

    private final CompanyRepository companyRepository;
    private final DividendRepository dividendRepository;

    // 외부에서 접근하여 저장되도록
    public Company save(String ticker) {
        boolean exists = this.companyRepository.existsByTicker(ticker);
        if (exists) {
            throw new RuntimeException("already exists ticker");
        }

        return this.storeCompanyAndDividend(ticker);
    }

    // 클래스 내부에서 호출되어 회사 정보 저장
    private Company storeCompanyAndDividend(String ticker) {
        // ticker를 기준으로 회사를 스크래핑
        Company company = this.yahooFinanceScraper.scrapCompanyByTicker(ticker);
        if (ObjectUtils.isEmpty(company)) {
            throw new RuntimeException("failed to scrap ticker ->" + ticker);
        }

        // 해당 회사가 존재할 때, 회사의 배당금 정보를 스크래핑
        ScrapedResult scrapedResult = this.yahooFinanceScraper.scrap(company);

        // 스크래핑 결과 저장
        CompanyEntity companyEntity = this.companyRepository.save((new CompanyEntity(company)));
        List<DividendEntity> dividendEntityList = scrapedResult.getDividendEntities().stream()
                                        .map(e -> new DividendEntity(companyEntity.getId(), e))
                                        .collect(Collectors.toList());
        this.dividendRepository.saveAll(dividendEntityList);

        return company;
    }

    // 모든 회사 가져오기
    public Page<CompanyEntity> getAllCompany(Pageable pageable) {
        return this.companyRepository.findAll(pageable);
    }


    /*  Trie를 이용한 자동완성  */
    // 자동완성 - 회사명 저장
    public void addAutoCompleteKeyword(String keyword) {
        this.trie.put(keyword, null);
    }

    // 자동완성 - 회사명을 조회하는 메서드
    public List<String> autoComplete(String keyword) {
        return (List<String>) this.trie.prefixMap(keyword).keySet()
                .stream().collect(Collectors.toList());
    }

    // 자동완성 - 회사명 삭제
    public void deleteAutoCompleteKeyword(String keyword) {
        this.trie.remove(keyword);
    }
}
