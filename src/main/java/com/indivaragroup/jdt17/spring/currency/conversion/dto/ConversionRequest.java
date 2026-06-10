package com.indivaragroup.jdt17.spring.currency.conversion.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record ConversionRequest(
        @NotNull
        @Positive
        BigDecimal amount,

        @NotBlank
        String from,

        @NotBlank
        String to
) {}