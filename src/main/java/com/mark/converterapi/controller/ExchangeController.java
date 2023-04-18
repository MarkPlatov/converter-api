package com.mark.converterapi.controller;

import com.mark.converterapi.facade.CoinmarketServiceFacade;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/exchange")
@RequiredArgsConstructor
public class ExchangeController {

    private final CoinmarketServiceFacade coinmarketServiceFacade;

    @GetMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Exchange an amount of source currency to destination currency")
    @Parameters({
        @Parameter(name = "amountIntegerPart", description = "Integer part of source currency amount", example = "1000"),
        @Parameter(name = "amountFractionalPart", description = "Fractional part of source currency amount", example = "1000"),
        @Parameter(name = "sourceCmcId", description = "Source currency CoinMarketCap ID", example = "1"),
        @Parameter(name = "destinationCmcId", description = "Source currency CoinMarketCap ID", example = "2")
    })
    @ApiResponses({
        @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    public ResponseEntity<?> getExchange(@RequestParam String amountIntegerPart,
                                         @RequestParam String amountFractionalPart,
                                         @RequestParam Integer sourceCmcId,
                                         @RequestParam Integer destinationCmcId) {
        return ResponseEntity.ok(coinmarketServiceFacade.exchange(amountIntegerPart,
            amountFractionalPart,
            sourceCmcId,
            destinationCmcId));
    }

    @GetMapping(value = "/find", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Find currencies description by it's name part")
    @Parameters({
        @Parameter(name = "name", description = "Name part", example = "btc")
    })
    @ApiResponses({
        @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    public ResponseEntity<?> addCoin(@RequestParam String name) {
        return ResponseEntity.ok().body(coinmarketServiceFacade.getCoin(name));
    }
}
