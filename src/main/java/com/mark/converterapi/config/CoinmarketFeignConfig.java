package com.mark.converterapi.config;

import feign.RequestInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;

@RequiredArgsConstructor
public class CoinmarketFeignConfig {

    private static final String AUTH_HEADER_NAME = "X-CMC_PRO_API_KEY";

    @Value("${application.clients.coin-market-cap.key:}")
    private String authKey;


    @Bean
    public RequestInterceptor requestInterceptor() {
        return requestTemplate -> requestTemplate.header(AUTH_HEADER_NAME, authKey);
    }

}
