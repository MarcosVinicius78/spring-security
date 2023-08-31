package com.marcos.springsecurity.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.marcos.springsecurity.model.Loans;
import com.marcos.springsecurity.repository.LoanRepository;

@RestController
public class LoansController {
    
    @Autowired
    private LoanRepository loanRepository;

    @GetMapping("/myLoans")
    // @PostAuthorize("hasRole('ROOT')")
    public List<Loans> getLoanDetails(@RequestParam int id) {
        List<Loans> loans = loanRepository.findByCustomerIdOrderByStartDtDesc(id);
        if (loans != null ) {
            return loans;
        }else {
            return null;
        }
    }
}
