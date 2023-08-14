package com.marcos.springsecurity.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.marcos.springsecurity.model.Contact;



@Repository
public interface ContactRepository extends CrudRepository<Contact, Long> {
	
	
}
