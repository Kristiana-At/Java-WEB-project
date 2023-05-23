package com.example.JavaWEBbff.services;

import com.example.JavaWEBbff.exceptions.NotFoundException;
import com.example.JavaWEBbff.models.Card;
import com.example.JavaWEBbff.models.CardOwner;
import com.example.JavaWEBbff.models.Money;
import com.example.JavaWEBbff.models.resources.CreateCardDto;
import com.example.JavaWEBbff.repositories.CardOwnerRepository;
import com.example.JavaWEBbff.repositories.CardRepository;
import com.example.JavaWEBbff.repositories.MoneyRepository;
import org.apache.catalina.LifecycleState;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CardService {
    private CardRepository cardRepository;
    private CardOwnerRepository cardOwnerRepository;
    private MoneyRepository moneyRepository;

    public CardService(CardRepository cardRepository, CardOwnerRepository cardOwnerRepository, MoneyRepository moneyRepository) {
        this.cardRepository = cardRepository;
        this.cardOwnerRepository = cardOwnerRepository;
        this.moneyRepository = moneyRepository;
    }

    public Card createCard(CreateCardDto createCardResource){
        Optional<CardOwner> cardOwner = this.cardOwnerRepository.findById(createCardResource.getCardOwnerId());
        if(cardOwner.isEmpty()){
            throw new NotFoundException("Owner does not exist");
        }

        Card card = cardRepository.save(new Card(createCardResource.getCardType(), cardOwner.get()));
        this.moneyRepository.save(new Money(card));
        return card;
    }

    public Card getCard(Long id) {
        Optional<Card> card = this.cardRepository.findById(id);
        if(card.isEmpty()){
            throw new NotFoundException("Card does not exist");
        }
        return card.get();
    }
}
