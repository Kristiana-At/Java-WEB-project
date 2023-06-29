package com.example.JavaWEBbff.controllers;

import com.example.JavaWEBbff.models.Card;
import com.example.JavaWEBbff.models.Money;
import com.example.JavaWEBbff.models.resources.*;
import com.example.JavaWEBbff.models.Transaction;
import com.example.JavaWEBbff.models.resources.CreateCardDto;
import com.example.JavaWEBbff.models.resources.ExchangeMoneyDto;
import com.example.JavaWEBbff.services.CardService;
import com.example.JavaWEBbff.services.TransactionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/card")
public class CardController {
    private CardService cardService;
    private TransactionService transactionService;

    public CardController(CardService cardService, TransactionService transactionService) {
        this.cardService = cardService;
        this.transactionService = transactionService;
    }

    @PostMapping("/create")
    public Card createCard(@RequestBody CreateCardDto createCardResource) {
        return this.cardService.createCard(createCardResource);
    }

    @GetMapping("/{id}/load")
    public Card getCard(@PathVariable Long id) {
        return this.cardService.getCard(id);
    }

    @PostMapping("/exchange/money")
    public ResponseEntity<Money> exchangeMoney(@RequestBody ExchangeMoneyDto exchangeMoneyDto) {
        return new ResponseEntity<>(this.cardService.exchangeMoney(exchangeMoneyDto.getIban(),
                exchangeMoneyDto.getMoney(), exchangeMoneyDto.getCurrencyFrom(),
                exchangeMoneyDto.getCurrencyTo()), HttpStatus.OK);
    }

    @PostMapping("/send/money")
    public ResponseEntity<Money> sendMoney(@RequestBody SendMoneyDto sendMoneyDto) {
        return new ResponseEntity<>(this.transactionService.sendMoney(sendMoneyDto.getIbanFrom(),
                sendMoneyDto.getIbanTo(), sendMoneyDto.getMoney(),
                sendMoneyDto.getCurrency(), sendMoneyDto.getNote()), HttpStatus.OK);
    }

    @PostMapping("/add/money")
    public ResponseEntity<Money> addMoney(@RequestBody AddMoneyDto addMoneyResource) {
        return new ResponseEntity<>(this.cardService.addMoney(addMoneyResource.getIban(),
                addMoneyResource.getMoney(), addMoneyResource.getCurrency()), HttpStatus.OK);
    }

    @GetMapping("/transactions")
    public List<Transaction> showHistory(@RequestParam String iban) {
     return this.transactionService.getTransactions(iban);
    }
}
