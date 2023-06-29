package com.example.JavaWEBbff.services;

import com.example.JavaWEBbff.exceptions.DigitalWalletException;
import com.example.JavaWEBbff.exceptions.NotFoundException;
import com.example.JavaWEBbff.models.Card;
import com.example.JavaWEBbff.models.CardOwner;
import com.example.JavaWEBbff.models.Money;
import com.example.JavaWEBbff.models.enums.CardType;
import com.example.JavaWEBbff.models.enums.Currency;
import com.example.JavaWEBbff.models.resources.CreateCardDto;
import com.example.JavaWEBbff.repositories.CardOwnerRepository;
import com.example.JavaWEBbff.repositories.CardRepository;
import com.example.JavaWEBbff.repositories.MoneyRepository;
import org.springframework.stereotype.Service;

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

    public Money exchangeMoney(String iban, double money, String currencyFrom, String currencyTo){
        Optional<Card> card = this.cardRepository.findByIban(iban);
        if(card.isEmpty()){
            throw new NotFoundException("Card does not exist");
        }

        boolean isMastercard = card.get().getCardType() == CardType.MASTERCARD;

        Currency enumCurrencyFrom = Currency.valueOf(currencyFrom);
        Currency enumCurrencyTo = Currency.valueOf(currencyTo);

        Optional<Money> moneyFrom = this.moneyRepository.findByCardAndCurrency(card.get(), enumCurrencyFrom);
        if(moneyFrom.isEmpty() || (moneyFrom.get().getMoney() < money + 0.2)){
            throw new DigitalWalletException("Not enough money");
        }

        Optional<Money> moneyTo = this.moneyRepository.findByCardAndCurrency(card.get(), enumCurrencyTo);
        if(isMastercard) {
            moneyFrom.get().setMoney(moneyFrom.get().getMoney() - (money + 0.2));
        } else {
            moneyFrom.get().setMoney(moneyFrom.get().getMoney() - money);
        }

        this.moneyRepository.save(moneyFrom.get());

        double moneyToSave = (money * enumCurrencyFrom.getBgnValue()) / enumCurrencyTo.getBgnValue();

        if(moneyTo.isPresent()){
            moneyTo.get().setMoney(moneyTo.get().getMoney() + moneyToSave);
            this.moneyRepository.save(moneyTo.get());
        } else{
            this.moneyRepository.save(new Money(card.get(), moneyToSave, enumCurrencyTo));
        }

        return moneyFrom.get();
    }

    public Money addMoney(String iban, double money, String currency){
        Optional<Card> card = this.cardRepository.findByIban(iban);
        if(card.isEmpty()){
            return new Money();
        }
        Currency enumCurrency = Currency.valueOf(currency);
        Optional<Money> existing = this.moneyRepository.findByCardAndCurrency(card.get(), enumCurrency);
        if(existing.isPresent()){
            existing.get().setMoney(existing.get().getMoney() + money);
            return this.moneyRepository.save(existing.get());
        }
        return this.moneyRepository.save(new Money(card.get(), money, enumCurrency));
    }
}
