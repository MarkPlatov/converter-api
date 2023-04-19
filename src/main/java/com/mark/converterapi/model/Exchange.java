package com.mark.converterapi.model;

import java.time.LocalDateTime;
import javax.persistence.*;

import com.mark.converterapi.dto.CurrencyAmount;
import lombok.*;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@Entity
@Table(name = "exchange")
public class Exchange {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String amountSourceIntegerPart;
    private String amountSourceFractionalPart;
    private Integer cmcIdSource;
    private String amountDestinationIntegerPart;
    private String amountDestinationFractionalPart;
    private Integer cmcIdDestination;
    private Double exchangeRate;
    private LocalDateTime requestTimestamp;

    public static Exchange of(CurrencyAmount sourceAmount, CurrencyAmount destinationAmount, Double exchangeRate) {
        Exchange exchange = new Exchange();
        exchange.setAmountSourceIntegerPart(sourceAmount.integerPart());
        exchange.setAmountSourceFractionalPart(sourceAmount.fractionalPart());
        exchange.setCmcIdSource(sourceAmount.currencyCmcId());
        exchange.setAmountDestinationIntegerPart(destinationAmount.integerPart());
        exchange.setAmountDestinationFractionalPart(destinationAmount.fractionalPart());
        exchange.setCmcIdDestination(destinationAmount.currencyCmcId());
        exchange.setExchangeRate(exchangeRate);
        exchange.setRequestTimestamp(LocalDateTime.now());
        return exchange;
    }

}

