package com.nathan.currencyconversionapi.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * Configuration class for WebClient.
 * Sets up the WebClient with appropriate memory limits and other configurations.
 */
@Configuration
public class WebClientConfig {

    /**
     * Creates a WebClient builder bean with increased memory buffer for handling larger responses.
     *
     * @return WebClient.Builder with custom configuration
     */
    @Bean
    public WebClient.Builder webClientBuilder() {
        // Increase the buffer size to handle larger responses
        ExchangeStrategies strategies = ExchangeStrategies.builder()
                .codecs(codecs -> codecs.defaultCodecs().maxInMemorySize(16 * 1024 * 1024)) // 16MB
                .build();

        return WebClient.builder()
                .exchangeStrategies(strategies);
    }
}
