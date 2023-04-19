package com.mark.converterapi.exception;


public class UnknownExchangeRateException extends RuntimeException {
    public UnknownExchangeRateException(Integer sourceCmcId, Integer destinationCmcId) {
        super("Can't convert cmcId = %s to cmcId = %s, cause exchange rate is unknown".formatted(sourceCmcId, destinationCmcId));
    }
}
