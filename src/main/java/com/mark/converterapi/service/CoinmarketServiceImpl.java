package com.mark.converterapi.service;

import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mark.converterapi.config.AppConfig;
import com.mark.converterapi.dto.CoinDto;
import com.mark.converterapi.dto.ExchangeRateRequest;
import com.mark.converterapi.dto.ExchangeRateResponse;
import com.mark.converterapi.mq.ProducerConsumer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CoinmarketServiceImpl implements CoinmarketService {
    private final ProducerConsumer producerConsumer;
    private final AppConfig appConfig;
    private final ObjectMapper objectMapper;

    @Override
    public List<CoinDto> getCoin(String name) {
        String response = producerConsumer.request(name, appConfig.getCoinQueue());
        try {
            CoinDto[] coins = objectMapper.readValue(response, CoinDto[].class);
            return List.of(coins);
        } catch (JsonProcessingException ex) {
            throw new RuntimeException("Can't deserialize response: " + response, ex);
        }
    }

    @Override
    public Double getExchangeRate(Integer sourceCmcId, Integer destinationCmcId) {
        String response = producerConsumer.request(new ExchangeRateRequest(sourceCmcId, destinationCmcId), appConfig.getExchangeRateQueue());
        try {
            return objectMapper.readValue(response, ExchangeRateResponse.class).getExchangeRate();
        } catch (JsonProcessingException ex) {
            throw new RuntimeException("Can't deserialize response: " + response, ex);
        }
    }
}
