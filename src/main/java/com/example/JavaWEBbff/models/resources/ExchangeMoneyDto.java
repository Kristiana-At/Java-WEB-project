package com.example.JavaWEBbff.models.resources;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ExchangeMoneyDto {
    private String iban;
    private double money;
    private String currencyFrom;
    private String currencyTo;
}
