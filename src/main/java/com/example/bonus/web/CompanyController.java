package com.example.bonus.web;

import com.example.bonus.model.Company;
import com.example.bonus.persist.entity.CompanyEntity;
import com.example.bonus.service.CompanyService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/company")
@AllArgsConstructor
public class CompanyController {

    private final CompanyService companyService;

    // 회사이름으로 배당금 검색 자동완성 API
    @GetMapping("/autocomplete")
    public ResponseEntity<?> autoComplete(@RequestParam String keyword) {
        var result = this.companyService.getCompanyNamesByKeyword(keyword);         // LIKE
//        var result = this.companyService.autoComplete(keyword);         // Trie
        return ResponseEntity.ok(result);
    }

    // 회사 리스트 조회 API
    @GetMapping
    @PreAuthorize("hasRole('READ')")
    public ResponseEntity<?> searchCompany(final Pageable pageable) {
        Page<CompanyEntity> companies = this.companyService.getAllCompany(pageable);
        return ResponseEntity.ok(companies);
    }

    // 배당금 저장 API
    @PostMapping
    @PreAuthorize("hasRole('WRITE')")         // 이 API는 쓰기 권한이 있는 사용자만 접근
    public ResponseEntity<?> addCompany(@RequestBody Company request) {
        String ticker = request.getTicker().trim();
        if (ObjectUtils.isEmpty(ticker)) {
            throw new RuntimeException("ticker is empty.");
        }

        Company company = this.companyService.save(ticker);
        this.companyService.addAutoCompleteKeyword(company.getName());

        return ResponseEntity.ok(company);
    }

    // 배당금 삭제 API
    @DeleteMapping
    public ResponseEntity<?> deleteCompany() {
        return null;
    }
}
