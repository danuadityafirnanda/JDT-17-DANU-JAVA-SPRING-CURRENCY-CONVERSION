package com.indivaragroup.jdt17.spring.currency.conversion.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.math.BigDecimal;
import java.util.List;

@ConfigurationProperties(prefix = "currency")
public record RateProperties(List<Rate> rates) {
    public record Rate(
            String from,
            String to,
            BigDecimal rate
    ) {}
}