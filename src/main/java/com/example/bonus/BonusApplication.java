package com.example.bonus;

import com.example.bonus.model.Company;
import com.example.bonus.scraper.Scraper;
import com.example.bonus.scraper.YahooFinanceScraper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//@SpringBootApplication
public class BonusApplication {

    public static void main(String[] args) {
//        SpringApplication.run(BonusApplication.class, args);

        Scraper scraper = new YahooFinanceScraper();

        var result = scraper.scrapCompanyByTicker("MMM");
        System.out.println(result);

    }

}
