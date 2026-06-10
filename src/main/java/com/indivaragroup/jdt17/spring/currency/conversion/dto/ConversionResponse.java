package com.indivaragroup.jdt17.spring.currency.conversion.dto;

import java.math.BigDecimal;

public record ConversionResponse(
        BigDecimal amount,
        String from,
        String to,
        BigDecimal rate,
        BigDecimal result
) {}