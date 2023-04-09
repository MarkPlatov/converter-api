package com.mark.converterapi.client;

import com.mark.converterapi.config.CoinmarketFeignConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;


@FeignClient(value = "coin-market-cap-client", url = "${application.clients.coin-market-cap.url}", configuration = CoinmarketFeignConfig.class)
public interface CoinmarketcapClient {

    @GetMapping("/uploaded/files/{filename}")
    String getFileByFilename(@PathVariable String filename, @RequestHeader("User-Agent") String userAgent);

}
