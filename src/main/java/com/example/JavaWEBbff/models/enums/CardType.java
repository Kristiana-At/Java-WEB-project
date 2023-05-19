package com.example.JavaWEBbff.models.enums;

public enum CardType {
    VISA("VISA"),
    MASTERCARD("MASTERCARD");

    private final String name;

    CardType(String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
