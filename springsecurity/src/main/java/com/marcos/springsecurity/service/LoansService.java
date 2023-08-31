package com.marcos.springsecurity.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.marcos.springsecurity.model.Loans;
import com.marcos.springsecurity.repository.LoanRepository;

@Service
public class LoansService {
    
    // @Autowired
    // private LoanRepository loanRepository;

    // public Loans getLoansDetails(String username){
    //     return loanRepository.loadLoanDetailsByUserName(username);
    // }
}
