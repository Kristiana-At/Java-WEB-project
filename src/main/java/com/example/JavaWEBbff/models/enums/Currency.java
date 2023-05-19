package com.example.JavaWEBbff.models.enums;

public enum Currency {
    EUR("EUR", 1.96),
    USD("USD", 1.81),
    GBP("GBP", 2.24),
    CAD("CAD", 1.34),
    BGN("BGN", 1.0),
    RUB("RUB", 0.026);

    private final String name;

    private final double bgnValue;

    Currency(String name, double bgnValue){
        this.name = name;
        this.bgnValue = bgnValue;
    }

    public String getName() {
        return name;
    }
    public double getBgnValue(){ return bgnValue;}

}
