package com.example.bonus.scraper;

import com.example.bonus.model.Company;
import com.example.bonus.model.ScrapedResult;

public interface Scraper {
    // ticker로 회사정보 스크래핑
    Company scrapCompanyByTicker(String ticker);
    // 해당 회사의 배당금 정보 스크래핑
    ScrapedResult scrap(Company company);
}
