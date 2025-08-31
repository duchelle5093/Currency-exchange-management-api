package com.nathan.currencyconversionapi.client;

import com.nathan.currencyconversionapi.model.ExchangeRateApiResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

/**
 * Client class responsible for communicating with the ExchangeRate API.
 * Uses WebClient to make non-blocking HTTP requests.
 */
@Component
public class ExchangeRateApiClient {

    /**
     * Base URL for the ExchangeRate API
     */
    @Value("${exchangerate.api.base-url}")
    private String apiBaseUrl;

    /**
     * API key for authentication with the ExchangeRate API
     */
    @Value("${exchangerate.api.key}")
    private String apiKey;

    /**
     * WebClient instance for making HTTP requests
     */
    private final WebClient webClient;

    /**
     * Constructor that initializes the WebClient
     */
    public ExchangeRateApiClient(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.build();
    }

    /**
     * Fetches the latest exchange rates for a specific base currency.
     *
     * @param baseCurrency The base currency to get rates for
     * @return A Mono containing the API response with the latest rates
     */
    public Mono<ExchangeRateApiResponse> getLatestRates(String baseCurrency) {
        return webClient.get()
                .uri(apiBaseUrl + "/v6/" + apiKey + "/latest/" + baseCurrency)
                .retrieve()
                .bodyToMono(ExchangeRateApiResponse.class);
    }
}
