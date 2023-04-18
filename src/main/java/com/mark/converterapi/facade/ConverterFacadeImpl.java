package com.mark.converterapi.facade;

import java.util.List;

import com.mark.converterapi.dto.CoinDto;
import com.mark.converterapi.dto.CurrencyAmount;
import com.mark.converterapi.model.Exchange;
import com.mark.converterapi.service.CoinmarketService;
import com.mark.converterapi.service.ExchangeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class ConverterFacadeImpl implements CoinmarketServiceFacade {

    private final CoinmarketService coinmarketService;
    private final ExchangeService exchangeService;

    @Override
    public Exchange exchange(String amountIntegerPart, String amountFractionalPart, Integer sourceCmcId, Integer destinationCmcId) {
        Double exchangeRate = coinmarketService.getExchangeRate(sourceCmcId, destinationCmcId);
        CurrencyAmount sourceAmount = new CurrencyAmount(amountIntegerPart, amountFractionalPart, sourceCmcId);
        CurrencyAmount destinationAmount = exchangeService.calc(sourceAmount, exchangeRate, destinationCmcId);
        return exchangeService.saveExchange(sourceAmount, destinationAmount, exchangeRate);
    }

    @Override
    public List<CoinDto> getCoin(String name) {
        return coinmarketService.getCoin(name);
    }

}
