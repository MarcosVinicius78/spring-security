package com.marcos.springsecurity.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.marcos.springsecurity.model.Customer;
import java.util.List;


public interface CustomerRepository extends JpaRepository<Customer, Long>{
    List<Customer> findByEmail(String email);
}
