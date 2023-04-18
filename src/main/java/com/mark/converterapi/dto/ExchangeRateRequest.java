package com.mark.converterapi.dto;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ExchangeRateRequest implements Serializable {

    private Integer sourceCmcId;
    private Integer destinationCmcId;

}
