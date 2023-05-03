package com.example.bonus.scraper;

import com.example.bonus.model.Company;
import com.example.bonus.model.ScrapedResult;

public interface Scraper {
    Company scrapCompanyByTicker(String ticker);
    ScrapedResult scrap(Company company);
}
