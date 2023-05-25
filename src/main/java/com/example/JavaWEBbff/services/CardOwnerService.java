package com.example.JavaWEBbff.services;

import com.example.JavaWEBbff.exceptions.DigitalWalletException;
import com.example.JavaWEBbff.exceptions.NotFoundException;
import com.example.JavaWEBbff.models.Card;
import com.example.JavaWEBbff.models.CardOwner;
import com.example.JavaWEBbff.models.resources.CreateCardOwnerDto;
import com.example.JavaWEBbff.repositories.CardOwnerRepository;
import com.example.JavaWEBbff.repositories.CardRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CardOwnerService {
    private CardRepository cardRepository;
    private CardOwnerRepository cardOwnerRepository;

    public CardOwnerService(CardRepository cardRepository, CardOwnerRepository cardOwnerRepository) {
        this.cardRepository = cardRepository;
        this.cardOwnerRepository = cardOwnerRepository;
    }

    public CardOwner createCardOwner(CreateCardOwnerDto createCardOwnerDto) {
        Optional<CardOwner> cardOwner = this.cardOwnerRepository.findByUsername(createCardOwnerDto.getUsername());
        if (cardOwner.isPresent()) {
            throw new DigitalWalletException("User already exists");
        }
        CardOwner newCardOwner = new CardOwner(createCardOwnerDto.getName(), createCardOwnerDto.getAge(),
                createCardOwnerDto.getUsername(), createCardOwnerDto.getPassword());
        return cardOwnerRepository.save(newCardOwner);

    }

    public CardOwner login(String username, String password) {
        Optional<CardOwner> cardOwner = this.cardOwnerRepository.findByUsernameAndPassword(username, password);
        if (cardOwner.isEmpty()) {
            throw new NotFoundException("Owner does not exist");
        }
        return cardOwner.get();
    }
}
