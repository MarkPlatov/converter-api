package com.mark.converterapi.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@NoArgsConstructor
@AllArgsConstructor
public class CoinDto {

    private Integer cmcId;

    private String name;

    private String symbol;

    private String sign;

    private String slug;

    private boolean isActive;

    private boolean isFiat;



}
