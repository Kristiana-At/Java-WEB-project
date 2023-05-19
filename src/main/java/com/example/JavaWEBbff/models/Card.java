package com.example.JavaWEBbff.models;

import com.example.JavaWEBbff.models.enums.CardType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Random;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Card {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String iban;

    @Enumerated(EnumType.STRING)
    private CardType cardType;

    @ManyToOne
    private CardOwner cardOwner;

    public Card(CardType cardType, CardOwner cardOwner) {
        this.cardType = cardType;
        this.cardOwner = cardOwner;
        this.iban = generateIban();
    }

    private String generateIban(){
        Random rand = new Random();
        StringBuilder card = new StringBuilder("BG");
        for (int i = 0; i < 14; i++)
        {
            int n = rand.nextInt(10);
            card.append(n);
        }
        return card.toString();
    }
}