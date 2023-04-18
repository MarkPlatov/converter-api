package com.mark.converterapi.service;

import java.util.List;

import com.mark.converterapi.dto.CoinDto;

public interface CoinmarketService {

    List<CoinDto> getCoin(String name);

    Double getExchangeRate(Integer sourceCmcId, Integer destinationCmcId);


}
