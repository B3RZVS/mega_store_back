package com.tpi_pais.mega_store.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfigs {
    @Bean
    public WebClient webClient() {
        return WebClient.builder()
                .baseUrl("https://sapapi.cavesoft.com.ar")
                .build();
    }
}

