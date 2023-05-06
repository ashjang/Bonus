package com.example.bonus.config;

import org.apache.commons.collections4.Trie;
import org.apache.commons.collections4.trie.PatriciaTrie;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    // app에서 trie가 하나만 존재하도록
    @Bean
    public Trie<String, String> trie() {
        return new PatriciaTrie<>();
    }

}
