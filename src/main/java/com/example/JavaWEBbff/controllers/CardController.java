package com.example.JavaWEBbff.controllers;

import com.example.JavaWEBbff.models.Card;
import com.example.JavaWEBbff.models.Money;
import com.example.JavaWEBbff.models.resources.CreateCardDto;
import com.example.JavaWEBbff.models.resources.ExchangeMoneyDto;
import com.example.JavaWEBbff.services.CardService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/card")
public class CardController {
    private CardService cardService;

    public CardController(CardService cardService) {
        this.cardService = cardService;
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
    public ResponseEntity<Money> exchangeMoney(@RequestBody ExchangeMoneyDto exchangeMoneyDto){
        return new ResponseEntity<>(this.cardService.exchangeMoney(exchangeMoneyDto.getIban(),
                exchangeMoneyDto.getMoney(), exchangeMoneyDto.getCurrencyFrom(),
                exchangeMoneyDto.getCurrencyTo()), HttpStatus.OK);
    }
}
