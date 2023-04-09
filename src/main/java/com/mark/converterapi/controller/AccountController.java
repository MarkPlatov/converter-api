package com.mark.converterapi.controller;

import java.util.List;

import com.mark.converterapi.dto.CoinDto;
import com.mark.converterapi.facade.CoinmarketServiceFacade;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/coin")
@RequiredArgsConstructor
public class AccountController {

    private final CoinmarketServiceFacade coinmarketServiceFacade;

    @GetMapping(value = "/{id}/", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Get coin")
    @Parameters({
        @Parameter(name = "id", description = "ID", example = "1")
    })
    @ApiResponses({
        @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    public ResponseEntity<?> getCoin(@PathVariable Integer id) {
        return ResponseEntity.ok(coinmarketServiceFacade.getCoin());
    }

    @PostMapping(value = "/{id}/add", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Add coin")
    @Parameters({
        @Parameter(name = "id", description = "ID", example = "1")
    })
    @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Coin request")
    @ApiResponses({
        @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    public ResponseEntity<?> addCoin(@PathVariable Integer id, @RequestBody List<CoinDto> request) {
        coinmarketServiceFacade.getCoin();
        return ResponseEntity.ok().build();
    }
}
