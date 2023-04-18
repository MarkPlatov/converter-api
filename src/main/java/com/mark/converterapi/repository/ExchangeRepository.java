package com.mark.converterapi.repository;

import com.mark.converterapi.model.Exchange;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExchangeRepository extends JpaRepository<Exchange, Integer> {

}
