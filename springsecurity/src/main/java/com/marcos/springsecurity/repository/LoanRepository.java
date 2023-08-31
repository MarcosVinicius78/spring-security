package com.marcos.springsecurity.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.security.access.prepost.PreAuthorize;

import com.marcos.springsecurity.model.Loans;

public interface LoanRepository extends CrudRepository<Loans, Long> {
	
	// @PreAuthorize("hasRole('USER')")
	List<Loans> findByCustomerIdOrderByStartDtDesc(int customerId);

}