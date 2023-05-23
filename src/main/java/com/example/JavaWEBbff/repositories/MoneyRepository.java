package com.example.JavaWEBbff.repositories;

import com.example.JavaWEBbff.models.Card;
import com.example.JavaWEBbff.models.Money;
import com.example.JavaWEBbff.models.enums.Currency;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MoneyRepository extends JpaRepository<Money, Integer> {
    Optional<Money> findFirstByCardOrderById(Card card);
    Optional<Money> findByCardAndCurrency(Card card, Currency currency);
}
