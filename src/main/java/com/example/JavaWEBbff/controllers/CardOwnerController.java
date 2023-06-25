package com.example.JavaWEBbff.controllers;

import com.example.JavaWEBbff.models.CardOwner;
import com.example.JavaWEBbff.models.resources.CreateCardOwnerDto;
import com.example.JavaWEBbff.services.CardOwnerService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class CardOwnerController {
    private CardOwnerService cardOwnerService;

    public CardOwnerController(CardOwnerService cardOwnerService){
        this.cardOwnerService = cardOwnerService;
    }

    @GetMapping("/login")
    public CardOwner login(@RequestParam String username,  @RequestParam String password){
        return this.cardOwnerService.login(username, password);
    }

    @PostMapping("/create")
    public CardOwner create(@RequestBody CreateCardOwnerDto createCardOwnerDto){
        return this.cardOwnerService.createCardOwner(createCardOwnerDto);
    }
}
