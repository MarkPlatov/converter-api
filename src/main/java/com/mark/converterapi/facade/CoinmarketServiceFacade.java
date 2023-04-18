package com.mark.converterapi.facade;

import java.util.List;

import com.mark.converterapi.dto.CoinDto;
import com.mark.converterapi.model.Exchange;


public interface CoinmarketServiceFacade {

    Exchange exchange(String amountIntegerPart, String amountFractionalPart, Integer cmcIdSource, Integer cmcIdDestination);

    List<CoinDto> getCoin(String name);
}
