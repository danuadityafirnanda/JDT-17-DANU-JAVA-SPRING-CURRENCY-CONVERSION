package com.indivaragroup.jdt17.spring.currency.conversion.converter;

import com.indivaragroup.jdt17.spring.currency.conversion.config.RateProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import java.math.BigDecimal;

@Component
@RequiredArgsConstructor
public class CurrencyConverter {

    private final RateProperties rateProperties;

    public BigDecimal convert(BigDecimal amount, String from, String to) {
        BigDecimal rate = rateProperties.rates().stream()
                .filter(r -> r.from().equalsIgnoreCase(from) && r.to().equalsIgnoreCase(to))
                .map(RateProperties.Rate::rate)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Rate tidak ditemukan untuk " + from + " ke " + to));

        return amount.multiply(rate);
    }
}