package com.example.bonus.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

// 스크랩한 결과를 주고받기 위한 클래스
@Data
@AllArgsConstructor
public class ScrapedResult {
    private Company company;
    private List<Dividend> dividendEntities;
    public ScrapedResult() {
        this.dividendEntities = new ArrayList<>();
    }
}
