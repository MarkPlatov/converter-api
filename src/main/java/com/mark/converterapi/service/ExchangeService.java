package com.mark.converterapi.service;

import com.mark.converterapi.dto.CurrencyAmount;
import com.mark.converterapi.model.Exchange;

public interface ExchangeService {

    CurrencyAmount calc(CurrencyAmount sourceAmount, Double exchangeRate, Integer destinationCmcId);

    Exchange saveExchange(CurrencyAmount sourceAmount, CurrencyAmount destinationAmount, Double exchangeRate);

}
