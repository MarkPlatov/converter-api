package com.mark.converterapi.service;

import java.math.BigDecimal;

import com.mark.converterapi.dto.CurrencyAmount;
import com.mark.converterapi.model.Exchange;
import com.mark.converterapi.repository.ExchangeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ExchangeServiceImpl implements ExchangeService {

    private final ExchangeRepository exchangeRepository;


    @Override
    public CurrencyAmount calc(CurrencyAmount sourceAmount, Double exchangeRate, Integer destinationCmcId) {
        BigDecimal destinationAmount = sourceAmount.toBigDecimal().multiply(BigDecimal.valueOf(exchangeRate));
        String destinationStr = destinationAmount.toPlainString();
        int dotIndex = destinationStr.indexOf('.');
        String integerPart = destinationStr.substring(0, dotIndex);
        String fractionalPart = trimTrailingZeros(destinationStr.substring(dotIndex + 1));
        return new CurrencyAmount(integerPart, fractionalPart, destinationCmcId);
    }

    private String trimTrailingZeros(String str) {
        int newStrLen = str.toCharArray().length;
        while (newStrLen > 0 && str.charAt(newStrLen - 1) == '0') {
            newStrLen--;
        }
        return str.substring(0, newStrLen);
    }

    @Override
    public Exchange saveExchange(CurrencyAmount sourceAmount, CurrencyAmount destinationAmount, Double exchangeRate) {
        Exchange exchange = Exchange.of(sourceAmount, destinationAmount, exchangeRate);
        return exchangeRepository.save(exchange);
    }

}
