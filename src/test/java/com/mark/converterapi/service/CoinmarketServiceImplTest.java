package com.mark.converterapi.service;

import java.time.LocalDateTime;
import java.util.List;

import com.mark.converterapi.config.AppConfig;
import com.mark.converterapi.dto.CoinDto;
import com.mark.converterapi.dto.ExchangeRateRequest;
import com.mark.converterapi.dto.ExchangeRateResponse;
import com.mark.converterapi.mq.ProducerConsumer;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@SpringBootTest
@ActiveProfiles({"devlocal"})
class CoinmarketServiceImplTest {

    private final CoinDto DEFAULT_COIN_DTO = new CoinDto(
        1,
        "Bitcoin",
        "BTC",
        "$",
        "bitcoin",
        true,
        false
    );

    private final ExchangeRateResponse DEFAULT_EXCHANGE_RATE = new ExchangeRateResponse(
        1,
        2,
        1.,
        LocalDateTime.now()
    );

    @Autowired
    private CoinmarketServiceImpl coinmarketService;

    @Autowired
    private AppConfig appConfig;

    @MockBean
    private ProducerConsumer producerConsumer;


    @Test
    void getCoinNull() {
        List<CoinDto> actual = coinmarketService.getCoin("btc");
        assertEquals(0, actual.size());
    }

    @Test
    void getCoin() {
        String request = "btc";
        String toBeReturned = "[{" +
            "\"cmcId\":\"" + DEFAULT_COIN_DTO.getCmcId() + "\"," +
            "\"name\":\"" + DEFAULT_COIN_DTO.getName() + "\"," +
            "\"symbol\":\"" + DEFAULT_COIN_DTO.getSymbol() + "\"," +
            "\"sign\":\"" + DEFAULT_COIN_DTO.getSign() + "\"," +
            "\"slug\":\"" + DEFAULT_COIN_DTO.getSlug() + "\"," +
            "\"active\":\"" + DEFAULT_COIN_DTO.isActive() + "\"," +
            "\"fiat\":\"" + DEFAULT_COIN_DTO.isFiat() + "\"" +
            "}]";

        Mockito.doReturn(toBeReturned)
            .when(producerConsumer)
            .request(request, appConfig.getCoinQueue());

        List<CoinDto> actual = coinmarketService.getCoin(request);

        assertEquals(DEFAULT_COIN_DTO, actual.get(0));
    }

    @Test
    void getExchangeRateNull() {
        Double actual = coinmarketService.getExchangeRate(1, 2);
        assertNull(actual);
    }

    @Test
    void getExchangeRate() {
        ExchangeRateRequest request = new ExchangeRateRequest(
            DEFAULT_EXCHANGE_RATE.getSourceCmcId(),
            DEFAULT_EXCHANGE_RATE.getDestinationCmcId()
        );
        String toBeReturned = "{" +
            "\"sourceCmcId\":\"" + DEFAULT_EXCHANGE_RATE.getSourceCmcId() + "\"," +
            "\"destinationCmcId\":\"" + DEFAULT_EXCHANGE_RATE.getDestinationCmcId() + "\"," +
            "\"exchangeRate\":\"" + DEFAULT_EXCHANGE_RATE.getExchangeRate() + "\"," +
            "\"lastUpdated\":\"" + DEFAULT_EXCHANGE_RATE.getLastUpdated() + "\"" +
            "}";

        Mockito.doReturn(toBeReturned)
            .when(producerConsumer)
            .request(request, appConfig.getExchangeRateQueue());

        Double actual = coinmarketService.getExchangeRate(
            DEFAULT_EXCHANGE_RATE.getSourceCmcId(),
            DEFAULT_EXCHANGE_RATE.getDestinationCmcId()
        );

        assertEquals(DEFAULT_EXCHANGE_RATE.getExchangeRate(), actual);
    }
}