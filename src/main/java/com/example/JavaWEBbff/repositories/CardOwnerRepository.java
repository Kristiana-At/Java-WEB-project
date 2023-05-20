package com.example.JavaWEBbff.repositories;

import com.example.JavaWEBbff.models.CardOwner;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CardOwnerRepository extends JpaRepository<CardOwner, Integer> {
    Optional<CardOwner> findById(Long id);
    Optional<CardOwner> findByUsernameAndPassword(String username, String password);
}
