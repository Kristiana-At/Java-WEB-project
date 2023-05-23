package com.example.JavaWEBbff.models.resources;

import com.example.JavaWEBbff.models.enums.CardType;
import lombok.Getter;

@Getter
public class CreateCardDto {
    private CardType cardType;
    private Long cardOwnerId;
}
