package com.example.bonus.web;

import com.example.bonus.model.Auth;
import com.example.bonus.security.TokenProvider;
import com.example.bonus.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final MemberService memberService;
    private final TokenProvider tokenProvider;

    // 회원가입 API
    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody Auth.SignUp request) {
        var result = this.memberService.register(request);
        log.info("user signup -> " + request.getUsername());
        return ResponseEntity.ok(result);
    }

    // 로그인 API
    @PostMapping("/signin")
    public ResponseEntity<?> signin(@RequestBody Auth.SignIn request) {
        // 1. username, pw가 맞는지
        var member = this.memberService.authenticate(request);
        // 2. 인증이 됐을 때 토큰생성하여 반환
        var token = this.tokenProvider.generateToken(member.getUsername(), member.getRoles());

        log.info("user login -> " + request.getUsername());
        return ResponseEntity.ok(token);
    }
}
