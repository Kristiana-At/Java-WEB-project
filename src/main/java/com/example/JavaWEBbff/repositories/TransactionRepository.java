package com.example.JavaWEBbff.repositories;

import com.example.JavaWEBbff.models.Card;
import com.example.JavaWEBbff.models.CardOwner;
import com.example.JavaWEBbff.models.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TransactionRepository extends JpaRepository<Transaction, Integer> {
    Optional<Transaction> findAllBySender(Card sender);
    List<Transaction> findAllBySenderOrReceiverOrderByDateDesc(Card sender, Card receiver);
}
