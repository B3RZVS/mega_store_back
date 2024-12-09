package com.tpi_pais.mega_store.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

// Anotación que indica que esta clase contiene configuraciones de Spring
@Configuration
public class WebClientConfigs {

    /**
     * Define un bean de tipo WebClient que puede ser inyectado y reutilizado
     * en cualquier parte de la aplicación.
     *
     * @return un cliente WebClient preconfigurado con una URL base.
     */
    @Bean
    public WebClient webClient() {
        // Configura un cliente WebClient con una URL base específica
        return WebClient.builder()
                .baseUrl("https://sapapi.cavesoft.com.ar") // URL base para todas las solicitudes
                .build(); // Construye el cliente
    }
}
