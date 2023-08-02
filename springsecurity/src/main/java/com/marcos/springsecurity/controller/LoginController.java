package com.marcos.springsecurity.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.marcos.springsecurity.model.Customer;
import com.marcos.springsecurity.repository.CustomerRepository;

@RestController
public class LoginController {
    
    @Autowired PasswordEncoder passwordEncoder;

    @Autowired
    private CustomerRepository customerRepository;
    
    @PostMapping("/register")
    public ResponseEntity<String> registreUser(@RequestBody Customer customer){
        Customer saveCustomer = null;
        ResponseEntity<String> response = null;

        
        try{
            if (customerRepository.findByEmail(customer.getEmail()).size() > 0) {
                return ResponseEntity.status(HttpStatus.OK).body("E-mail ja cadastrado");
            }

            String hashPwd = passwordEncoder.encode(customer.getPwd());
            customer.setPwd(hashPwd);
            saveCustomer = customerRepository.save(customer);
            if (saveCustomer.getId() > 0) {
                response = ResponseEntity.status(HttpStatus.CREATED).body("criado com sucesso");
            }
        } catch (Exception e) {
            response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao registrar" + e.getMessage());
        }
        return response;
    }

}
