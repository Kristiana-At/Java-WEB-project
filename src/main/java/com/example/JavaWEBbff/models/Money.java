package com.example.JavaWEBbff.models;

import com.example.JavaWEBbff.models.enums.Currency;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Setter
@Getter
@AllArgsConstructor
public class Money {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private Double money;

    @Enumerated(EnumType.STRING)
    private Currency currency;

    @ManyToOne
    @JsonIgnore
    private Card card;

    public Money(){
        this.money = 0.0;
        this.currency = Currency.BGN;
    }

    public Money(Card card){
        this.money = 0.0;
        this.currency = Currency.BGN;
        this.card = card;
    }

    public Money(Card card, double money, Currency currency){
        this.money = money;
        this.currency = currency;
        this.card = card;
    }
}
