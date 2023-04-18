package com.mark.converterapi.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class CoinDto {

    private Integer cmcId;

    private String name;

    private String symbol;

    private String sign;

    private String slug;

    private boolean isActive;

    private boolean isFiat;

}
