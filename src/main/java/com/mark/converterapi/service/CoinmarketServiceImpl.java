package com.mark.converterapi.service;

import java.util.List;

import com.mark.converterapi.model.Coin;
import com.mark.converterapi.repository.CoinmarketRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CoinmarketServiceImpl implements CoinmarketService {

    private final CoinmarketRepository repository;

    @Override
    public List<Coin> getCheckingAccounts() {
        return repository.findAll();
    }

}
