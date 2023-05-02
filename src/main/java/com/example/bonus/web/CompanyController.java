package com.example.bonus.web;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

public class CompanyController {

    // 회사이름으로 배당금 검색 자동완성 API
    @GetMapping("/company/autocomplete")
    public ResponseEntity<?> autoComplete(@RequestParam String keyword) {
        return null;
    }

    // 회사 리스트 조회 API
    @GetMapping("/company")
    public ResponseEntity<?> searchCompany() {
        return null;
    }

    // 배당금 저장 API
    @PostMapping("/company")
    public ResponseEntity<?> addCompany() {
        return null;
    }

    // 배당금 삭제 API
    @DeleteMapping("/company")
    public ResponseEntity<?> deleteCompany() {
        return null;
    }
}
