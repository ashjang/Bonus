package com.example.bonus.web;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/company")
public class CompanyController {

    // 회사이름으로 배당금 검색 자동완성 API
    @GetMapping("/autocomplete")
    public ResponseEntity<?> autoComplete(@RequestParam String keyword) {
        return null;
    }

    // 회사 리스트 조회 API
    @GetMapping
    public ResponseEntity<?> searchCompany() {
        return null;
    }

    // 배당금 저장 API
    @PostMapping
    public ResponseEntity<?> addCompany() {
        return null;
    }

    // 배당금 삭제 API
    @DeleteMapping
    public ResponseEntity<?> deleteCompany() {
        return null;
    }
}
