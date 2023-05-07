package com.example.bonus.config;

import org.apache.commons.collections4.Trie;
import org.apache.commons.collections4.trie.PatriciaTrie;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class AppConfig {

    // app에서 trie가 하나만 존재하도록
    @Bean
    public Trie<String, String> trie() {
        return new PatriciaTrie<>();
    }

    // MemberService에서 final로 선언한 passwordEncoder에서 이 빈을 사용
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}

