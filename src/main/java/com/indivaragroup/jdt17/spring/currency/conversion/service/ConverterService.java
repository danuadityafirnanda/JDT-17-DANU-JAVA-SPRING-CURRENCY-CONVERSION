package com.indivaragroup.jdt17.spring.currency.conversion.service;

import com.indivaragroup.jdt17.spring.currency.conversion.config.RateProperties;
import com.indivaragroup.jdt17.spring.currency.conversion.converter.CurrencyConverter;
import com.indivaragroup.jdt17.spring.currency.conversion.dto.ConversionRequest;
import com.indivaragroup.jdt17.spring.currency.conversion.dto.ConversionResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class ConverterService {

    private final CurrencyConverter currencyConverter;
    private final RateProperties rateProperties;

    public ConversionResponse convertCurrency(ConversionRequest request) {
        BigDecimal result = currencyConverter.convert(
                request.amount(),
                request.from(),
                request.to()
        );

        BigDecimal rate = rateProperties.rates().stream()
                .filter(r -> r.from().equalsIgnoreCase(request.from()) && r.to().equalsIgnoreCase(request.to()))
                .map(RateProperties.Rate::rate)
                .findFirst()
                .orElse(BigDecimal.ZERO);

        return new ConversionResponse(
                request.amount(),
                request.from().toUpperCase(),
                request.to().toUpperCase(),
                rate,
                result
        );
    }
}