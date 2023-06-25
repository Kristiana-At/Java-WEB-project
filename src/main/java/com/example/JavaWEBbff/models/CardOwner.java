package com.example.JavaWEBbff.models;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CardOwner {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private Integer age;

    @OneToOne
    private Card incomeCard;

    @OneToMany(mappedBy = "cardOwner")
    private List<Card> cards;

    private String username;

    private String password;

    public CardOwner(String name, Integer age, String username, String password) {
        this.name = name;
        this.age = age;
        this.username = username;
        this.password = password;
    }
}