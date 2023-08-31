package com.marcos.springsecurity.controller;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.access.prepost.PreFilter;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.marcos.springsecurity.model.Contact;
import com.marcos.springsecurity.repository.ContactRepository;

@RestController
public class ContactController {
    
     @Autowired
    private ContactRepository contactRepository;


    //filterObject é p request body que recebemos no metodo, e o contactName é o atributo que temos em nosso model contact
    //em nosso front, quando for salvar, precisa mandar uma lista e não um unico objeto
    @PostMapping("/myContact")
    @PreFilter("filterObject.contactName != 'Test'")
    // @PostFilter("filterObject.contactName != 'Test'")
    public List<Contact> saveContactInquiryDetails(@RequestBody List<Contact> contacts) {
        Contact contact = contacts.get(0);

        contact.setContactId(getServiceReqNumber());
        contact.setCreateDt(new Date(System.currentTimeMillis()));
        contact = contactRepository.save(contact);
        List<Contact> saveContacts = new ArrayList<>();
        saveContacts.add(contact);
        return saveContacts;
    }

    public String getServiceReqNumber() {
        Random random = new Random();
        int ranNum = random.nextInt(999999999 - 9999) + 9999;
        return "SR"+ranNum;
    }
}
