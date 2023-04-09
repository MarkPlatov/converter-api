package com.mark.converterapi.facade;

import java.util.List;

import com.mark.converterapi.model.Coin;
import com.mark.converterapi.service.CoinmarketService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class CoinmarketServiceFacadeImpl implements CoinmarketServiceFacade {

    private final CoinmarketService coinmarketService;

    @Override
    public List<Coin> getCoin() {
        return coinmarketService.getCheckingAccounts();
    }

}
