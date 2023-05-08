package com.example.bonus.security;

import com.example.bonus.service.MemberService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.List;

@Component
@RequiredArgsConstructor
public class TokenProvider {

    private static final String KEY_ROLES = "roles";
    private static final long TOKEN_EXPIRE_TIME = 1000 * 60 * 60;       // 한 시간

    private final MemberService memberService;

    @Value("{spring.jwt.secret}")
    private String secretKey;

    /**
     * 토큰 생성(발급)
     * @param username
     * @param roles
     * @return
     */

    public String generateToken(String username, List<String> roles) {
        System.out.println("generateToken");
        // 사용자 권한 정보 저장하는 claim
        Claims claims = Jwts.claims().setSubject(username);
        claims.put(KEY_ROLES, roles);

        var now = new Date();
        var expiredDate = new Date(now.getTime() + TOKEN_EXPIRE_TIME);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)           // 토큰 생성 시간
                .setExpiration(expiredDate) // 토큰 만료 시간
                .signWith(SignatureAlgorithm.HS512, this.secretKey)    // 사용할 알고리즘, 비밀키
                .compact();
    }

    // 토큰으로부터 username 받기
    public String getUsername(String token) {
        System.out.println("getUsername");
        return this.parseClaims(token).getSubject();
    }

    // 토큰의 유효성
    public boolean validateToken(String token) {
        System.out.println("validateToken");
        // token이 빈 값일 때
        if (!StringUtils.hasText(token)) return false;

        var claims = this.parseClaims(token);
        // 현재 시간과 비교
        System.out.println(claims.getExpiration().before(new Date()));
        return !claims.getExpiration().before(new Date());
    }

    // 토큰으로부터 claim을 가져오는
    private Claims parseClaims(String token) {
        System.out.println("parseClaims");
        try {
            return Jwts.parser().setSigningKey(this.secretKey).parseClaimsJws(token).getBody();
        } catch (ExpiredJwtException e) {
            return e.getClaims();
        }
    }

    // jwt token으로부터 인증정보를 가져오는
    public Authentication getAuthentication(String jwt) {
        System.out.println("getAuthentication");
        UserDetails userDetails = this.memberService.loadUserByUsername(this.getUsername(jwt));
        return new UsernamePasswordAuthenticationToken(
                userDetails, "", userDetails.getAuthorities());
    }
}
