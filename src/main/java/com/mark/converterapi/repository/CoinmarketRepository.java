package com.mark.converterapi.repository;

import com.mark.converterapi.model.Coin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CoinmarketRepository extends JpaRepository<Coin, Integer> {

}
