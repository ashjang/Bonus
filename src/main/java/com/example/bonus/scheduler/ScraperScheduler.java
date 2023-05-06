package com.example.bonus.scheduler;

import com.example.bonus.model.Company;
import com.example.bonus.model.ScrapedResult;
import com.example.bonus.persist.CompanyRepository;
import com.example.bonus.persist.DividendRepository;
import com.example.bonus.persist.entity.CompanyEntity;
import com.example.bonus.persist.entity.DividendEntity;
import com.example.bonus.scraper.YahooFinanceScraper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@AllArgsConstructor
public class ScraperScheduler {

    private final CompanyRepository companyRepository;
    private final DividendRepository dividendRepository;

    private final YahooFinanceScraper yahooFinanceScraper;

    // 일정 주기마다 수행 (매일 정각)
    @Scheduled(cron = "${scheduler.scrap.yahoo}")
    public void yahooFinanceScheduling() {
        log.info("Scraping scheduler is started.");

        // 1. 저장된 회사 목록을 조회
        List<CompanyEntity> companies = this.companyRepository.findAll();

        // 2. 회사마다 배당금 정보를 새로 스크래핑
        for (var company: companies) {
            ScrapedResult scrapedResult = yahooFinanceScraper.scrap(new Company(company.getTicker(), company.getName()));

            // 3. 스크래핑한 배당금 정보 중 DB에 없는 값은 저장
            scrapedResult.getDividendEntities().stream()
                    // dividend model을 dividendEntity로 매핑
                    .map(e -> new DividendEntity(company.getId(), e))
                    // element를 하나씩 dividend repository에 삽입
                    .forEach(e -> {
                        boolean exists = this.dividendRepository.existsByCompanyIdAndDate(e.getCompanyId(), e.getDate());
                        if (!exists) {
                            this.dividendRepository.save(e);
                        }
                    });

            // 연속적인 스크래핑 때문에 서버에 과부하되지 않도록 처리
            try {
                Thread.sleep(3000);     // 3 seconds
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

    }
}
