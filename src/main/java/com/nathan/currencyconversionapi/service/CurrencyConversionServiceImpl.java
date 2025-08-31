package com.nathan.currencyconversionapi.service;


import com.nathan.currencyconversionapi.model.ConversionRequest;
import com.nathan.currencyconversionapi.client.ExchangeRateApiClient;
import com.nathan.currencyconversionapi.exception.CurrencyConversionException;
import com.nathan.currencyconversionapi.model.ConversionRequest;
import com.nathan.currencyconversionapi.model.ConversionResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

/**
 * Implementation of the CurrencyConversionService interface.
 * Contains the business logic for converting currencies using the ExchangeRate API.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class CurrencyConversionServiceImpl implements CurrencyConversionService {

    /**
     * Client for communicating with the ExchangeRate API
     */
    private final ExchangeRateApiClient exchangeRateApiClient;

    /**
     * {@inheritDoc}
     */
    @Override
    public Mono<ConversionResult> convertCurrency(ConversionRequest request) {
        log.info("Converting {} {} to {}", request.getAmount(), request.getSourceCurrency(), request.getTargetCurrency());

        // Validate input
        if (request.getAmount() == null || request.getAmount() <= 0) {
            return Mono.error(new CurrencyConversionException("Amount must be greater than zero"));
        }

        if (request.getSourceCurrency() == null || request.getTargetCurrency() == null) {
            return Mono.error(new CurrencyConversionException("Source and target currencies must be specified"));
        }

        String sourceCurrency = request.getSourceCurrency().toUpperCase();
        String targetCurrency = request.getTargetCurrency().toUpperCase();

        log.debug("Calling ExchangeRate API for base currency: {}", sourceCurrency);

        // Fetch latest exchange rates for the source currency
        return exchangeRateApiClient.getLatestRates(sourceCurrency)
                .doOnNext(response -> log.debug("Received API response: {}", response))
                .flatMap(response -> {
                    // Check if the API request was successful
                    if (!response.isSuccess()) {
                        log.error("API request failed. Response: {}", response);
                        return Mono.error(new CurrencyConversionException("Failed to retrieve exchange rates"));
                    }

                    log.debug("Available currencies in response: {}", response.getRates().keySet());

                    // Check if the target currency exists in the rates
                    if (!response.getRates().containsKey(targetCurrency)) {
                        return Mono.error(new CurrencyConversionException(
                                "Target currency " + targetCurrency + " not found in available rates"));
                    }

                    // Get the exchange rate and calculate the converted amount
                    Double exchangeRate = response.getRates().get(targetCurrency);
                    Double convertedAmount = request.getAmount() * exchangeRate;

                    log.info("Conversion successful. Rate: {}, Converted amount: {}", exchangeRate, convertedAmount);

                    // Build and return the conversion result
                    return Mono.just(ConversionResult.builder()
                            .sourceCurrency(sourceCurrency)
                            .targetCurrency(targetCurrency)
                            .sourceAmount(request.getAmount())
                            .targetAmount(convertedAmount)
                            .exchangeRate(exchangeRate)
                            .timestamp(LocalDateTime.now())
                            .build());
                })
                .doOnError(error -> log.error("Error during currency conversion: {}", error.getMessage()));
    }
}