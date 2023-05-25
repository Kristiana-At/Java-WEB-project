package com.example.JavaWEBbff.controllers;

import com.example.JavaWEBbff.models.CardOwner;
import com.example.JavaWEBbff.models.resources.CreateCardOwnerDto;
import com.example.JavaWEBbff.models.resources.LoginDto;
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
    public CardOwner login(@RequestBody LoginDto loginData){
        return this.cardOwnerService.login(loginData.getUsername(), loginData.getPassword());
    }

    @PostMapping("/create")
    public CardOwner create(@RequestBody CreateCardOwnerDto createCardOwnerDto){
        return this.cardOwnerService.createCardOwner(createCardOwnerDto);
    }
}
