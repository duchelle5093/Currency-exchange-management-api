package com.nathan.currencyconversionapi.service;


import com.nathan.currencyconversionapi.model.ConversionRequest;
import com.nathan.currencyconversionapi.model.ConversionResult;
import reactor.core.publisher.Mono;

/**
 * Service interface for currency conversion operations.
 * Defines the contract for implementing currency conversion functionality.
 */
public interface CurrencyConversionService {

    /**
     * Converts an amount from one currency to another using current exchange rates.
     *
     * @param request Contains the source currency, target currency, and amount to convert
     * @return A Mono containing the conversion result with all conversion details
     */
    Mono<ConversionResult> convertCurrency(ConversionRequest request);
}
