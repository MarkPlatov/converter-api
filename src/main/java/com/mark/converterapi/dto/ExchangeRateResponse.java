package com.mark.converterapi.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExchangeRateResponse {

    private Integer sourceCmcId;
    private Integer destinationCmcId;
    private Double exchangeRate;
    private LocalDateTime lastUpdated;

}
