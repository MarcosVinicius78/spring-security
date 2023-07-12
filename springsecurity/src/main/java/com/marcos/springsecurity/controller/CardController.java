package com.marcos.springsecurity.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CardController {
    

    @GetMapping("/cards")
    public String getCardsDetails(){
        return "Aqui está seus cards";
    }
}
