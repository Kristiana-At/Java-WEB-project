package com.example.JavaWEBbff.models.resources;

import lombok.Getter;

@Getter
public class SendMoneyDto {
    private String ibanFrom;
    private String ibanTo;
    private double money;
    private String currency;
    private String note;
}
