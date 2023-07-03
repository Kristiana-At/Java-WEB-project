package com.example.JavaWEBbff.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Card sender;

    @ManyToOne
    private Card receiver;

    private double money;

    private String note;

    private LocalDateTime date;

    public Transaction(Card cardFrom, Card cardTo, double money, String note, LocalDateTime date) {
        this.sender = cardFrom;
        this.receiver = cardTo;
        this.money = money;
        this.note = note;
        this.date = date;
    }
}
