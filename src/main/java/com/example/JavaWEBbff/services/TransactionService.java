package com.example.JavaWEBbff.services;

import com.example.JavaWEBbff.exceptions.DigitalWalletException;
import com.example.JavaWEBbff.exceptions.NotFoundException;
import com.example.JavaWEBbff.models.Card;
import com.example.JavaWEBbff.models.Money;
import com.example.JavaWEBbff.models.Transaction;
import com.example.JavaWEBbff.models.enums.CardType;
import com.example.JavaWEBbff.models.enums.Currency;
import com.example.JavaWEBbff.repositories.CardOwnerRepository;
import com.example.JavaWEBbff.repositories.CardRepository;
import com.example.JavaWEBbff.repositories.MoneyRepository;
import com.example.JavaWEBbff.repositories.TransactionRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class TransactionService {
    private TransactionRepository transactionRepository;
    private CardRepository cardRepository;
    private CardOwnerRepository cardOwnerRepository;
    private MoneyRepository moneyRepository;

    public TransactionService(CardRepository cardRepository, CardOwnerRepository cardOwnerRepository,
                              MoneyRepository moneyRepository, TransactionRepository transactionRepository) {
        this.cardRepository = cardRepository;
        this.cardOwnerRepository = cardOwnerRepository;
        this.moneyRepository = moneyRepository;
        this.transactionRepository = transactionRepository;
    }

    public Money sendMoney(String ibanFrom, String ibanTo, double money, String currency, String note) {
        Optional<Card> cardFrom = this.cardRepository.findByIban(ibanFrom);
        if (cardFrom.isEmpty()) {
            throw new NotFoundException("Card does not exist");
        }

        boolean isVisa = cardFrom.get().getCardType() == CardType.VISA;

        Optional<Card> cardTo = this.cardRepository.findByIban(ibanTo);
        if (cardTo.isEmpty()) {
            throw new NotFoundException("Card does not exist");
        }
        Currency enumCurrency = Currency.valueOf(currency);

        Optional<Money> moneyFrom = this.moneyRepository.findByCardAndCurrency(cardFrom.get(), enumCurrency);
        if (moneyFrom.isEmpty() || (moneyFrom.get().getMoney() < (money + 0.2))) {
            throw new DigitalWalletException("Not enough money");
        }

        Optional<Money> moneyTo = this.moneyRepository.findByCardAndCurrency(cardTo.get(), enumCurrency);
        if (moneyTo.isPresent()) {
            moneyTo.get().setMoney(moneyTo.get().getMoney() + money);
            this.moneyRepository.save(moneyTo.get());
        } else {
            this.moneyRepository.save(new Money(cardTo.get(), money, enumCurrency));
        }
        this.transactionRepository.save(new Transaction(cardFrom.get(), cardTo.get(), money,
                note, LocalDateTime.now()));

        if (isVisa) {
            moneyFrom.get().setMoney(moneyFrom.get().getMoney() - (money + 0.2));
        } else {
            moneyFrom.get().setMoney(moneyFrom.get().getMoney() - money);
        }
        this.moneyRepository.save(moneyFrom.get());
        return moneyFrom.get();
    }

    public List<Transaction> getTransactions(String iban) {
        Optional<Card> card = this.cardRepository.findByIban(iban);
        if (card.isEmpty()) {
            throw new NotFoundException("Card does not exist");
        }

        List<Transaction> transactions =
                this.transactionRepository.findAllBySenderOrReceiverOrderByDate(card.get(), card.get());

        return transactions;
    }

}
