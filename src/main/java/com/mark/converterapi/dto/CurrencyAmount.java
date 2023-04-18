package com.mark.converterapi.dto;

import java.math.BigDecimal;

import org.apache.commons.lang3.StringUtils;


public record CurrencyAmount(
    String integerPart,
    String fractionalPart,
    Integer currencyCmcId
) {

    private final static String ONLY_NUMBERS_REGEX = "^[0-9]+$";

    public CurrencyAmount {
        integerPart = StringUtils.isBlank(integerPart) ? "0" : integerPart;
        fractionalPart = StringUtils.isBlank(fractionalPart) ? "0" : fractionalPart;
        if (!integerPart.matches(ONLY_NUMBERS_REGEX) || !fractionalPart.matches(ONLY_NUMBERS_REGEX)) {
            throw new IllegalArgumentException("integerPart & fractionalPart should be numbers");
        }
    }

    public BigDecimal toBigDecimal() {
        return new BigDecimal(String.format("%s.%s", integerPart, fractionalPart));
    }
}