package com.example.JavaWEBbff.models.resources;

import lombok.Getter;

@Getter
public class AddMoneyDto {
    private String iban;
    private double money;
    private String currency;
}
