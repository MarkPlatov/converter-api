package com.mark.converterapi.service;

import com.mark.converterapi.dto.CurrencyAmount;
import com.mark.converterapi.exception.UnknownExchangeRateException;
import com.mark.converterapi.model.Exchange;
import lombok.Data;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles({"devlocal"})
class ExchangeServiceImplTest {

    private final CurrencyAmount DEFAULT_CURRENCY_AMOUNT = new CurrencyAmount("123", "456", 1);

    @Autowired
    private ExchangeServiceImpl exchangeService;

    @Test
    void calcMultOne() {
        calcTestTemplate(new CalcPayload(
            "1",
            "1",
            1,
            "1",
            "1",
            2,
            1.
        ));
    }

    @Test
    void calcMultZero() {
        calcTestTemplate(new CalcPayload(
            "1",
            "1",
            1,
            "0",
            "0",
            2,
            0.
        ));
    }

    @Test
    void calcMultTwoAndTwo() {
        calcTestTemplate(new CalcPayload(
            "1",
            "1",
            1,
            "2",
            "42",
            2,
            2.2
        ));
    }

    @Test
    void calcTrimZeros() {
        calcTestTemplate(new CalcPayload(
            "0000001000000",
            "000000100000000",
            1,
            "12000000",
            "0000012",
            2,
            12.
        ));
    }

    @Test
    void calcFractionalPartOverflow() {
        calcTestTemplate(new CalcPayload(
            "0",
            "9",
            1,
            "1",
            "8",
            2,
            2.
        ));
    }

    @Test
    public void calcThrowsException() {
        assertThrows(UnknownExchangeRateException.class, () -> exchangeService.calc(
            DEFAULT_CURRENCY_AMOUNT,
            null,
            2
            ));
    }

    @Test
    void saveExchange() {
        Double exchangeRate = 1.;
        Exchange actual = exchangeService.saveExchange(
            DEFAULT_CURRENCY_AMOUNT,
            DEFAULT_CURRENCY_AMOUNT,
            exchangeRate
        );

        Exchange expected = Exchange.of(DEFAULT_CURRENCY_AMOUNT, DEFAULT_CURRENCY_AMOUNT, exchangeRate);
        expected.setId(actual.getId());
        expected.setRequestTimestamp(actual.getRequestTimestamp());

        assertNotNull(actual.getId());
        assertNotNull(actual.getRequestTimestamp());
        assertEquals(expected, actual);
    }

    private void calcTestTemplate(CalcPayload payload) {
        CurrencyAmount actualDestinationAmount = exchangeService.calc(
            payload.sourceAmount,
            payload.exchangeRate,
            payload.expectedDestinationAmount.currencyCmcId()
        );
        assertEquals(payload.expectedDestinationAmount, actualDestinationAmount);

    }


    @Data
    private static class CalcPayload {

        private CurrencyAmount sourceAmount;
        private CurrencyAmount expectedDestinationAmount;
        private Double exchangeRate;

        CalcPayload(
            String sourceInt,
            String sourceFract,
            Integer sourceId,
            String expectedInt,
            String expectedFract,
            Integer expectedId,
            Double exchangeRate
        ) {
            sourceAmount = new CurrencyAmount(sourceInt, sourceFract, sourceId);
            expectedDestinationAmount = new CurrencyAmount(expectedInt, expectedFract, expectedId);
            this.exchangeRate = exchangeRate;
        }
    }
}