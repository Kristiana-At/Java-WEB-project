package com.example.JavaWEBbff.repositories;

import com.example.JavaWEBbff.models.Card;
import com.example.JavaWEBbff.models.CardOwner;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CardRepository extends JpaRepository<Card, Integer> {
    Optional<Card> findByIban(String iban);
    Optional<Card> findFirstByCardOwner(CardOwner owner);
}
