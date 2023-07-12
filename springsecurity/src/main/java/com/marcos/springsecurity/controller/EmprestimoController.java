package com.marcos.springsecurity.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EmprestimoController {
    
    @GetMapping("/emprestimo")
    public String getEmprestimoDetelhes(){
        return "Aqui está seus emprestimos";
    }
}
