package com.indivaragroup.jdt17.spring.currency.conversion.controller;

import com.indivaragroup.jdt17.spring.currency.conversion.config.RateProperties;
import com.indivaragroup.jdt17.spring.currency.conversion.dto.ConversionRequest;
import com.indivaragroup.jdt17.spring.currency.conversion.dto.ConversionResponse;
import com.indivaragroup.jdt17.spring.currency.conversion.service.ConverterService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class ConverterController {

    private final ConverterService converterService;
    private final RateProperties rateProperties;

    @Value("${app.name:Unknown Application}")
    private String appName;

    @Value("${app.message:Unknown Profile}")
    private String appMessage;

    @GetMapping("/converter")
    public ConversionResponse convert(@Valid ConversionRequest request) {
        return converterService.convertCurrency(request);
    }

    @GetMapping("/info")
    public Map<String, String> getInfo() {
        return Map.of("applicationName", appName);
    }

    @GetMapping("/profile")
    public Map<String, String> getProfile() {
        return Map.of("message", appMessage);
    }

    @GetMapping("/rates")
    public List<RateProperties.Rate> getRates() {
        return rateProperties.rates();
    }

    @ExceptionHandler(BindException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> handleBindException(BindException ex) {
        String errorMessage = ex.getFieldError() != null ? ex.getFieldError().getDefaultMessage() : "Invalid request";
        return Map.of("message", errorMessage);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
        String errorMessage = ex.getBindingResult().getFieldError() != null ? ex.getBindingResult().getFieldError().getDefaultMessage() : "Invalid request";
        return Map.of("message", errorMessage);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> handleIllegalArgument(IllegalArgumentException ex) {
        return Map.of("message", ex.getMessage());
    }
}