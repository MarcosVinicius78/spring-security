package com.marcos.springsecurity.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.marcos.springsecurity.model.Customer;
import com.marcos.springsecurity.repository.CustomerRepository;

@Service
public class EazyBankUserDetails implements UserDetailsService {

    @Autowired
    private CustomerRepository customerRepository;


    /*nessa classe a gente vai fazer a autenticação personalizada do usuario,
      ou seja, não precisamos deixar o banco com os seus atributos igual ao jdbc do spring
      para fazer a autenticação, vamos criar o nosso banco e os atributos que ele precisa para autenticar
     */
     @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        
        String userName, password = null;
        List<GrantedAuthority> authorities = null;
        List<Customer> customers = customerRepository.findByEmail(username);

        if (customers.size() == 0) {
            throw new UsernameNotFoundException("User details not found for the user: " + username);
        }else{
            userName = customers.get(0).getEmail();
            password = customers.get(0).getPwd();

            authorities = new ArrayList<>();
            authorities.add(new SimpleGrantedAuthority(customers.get(0).getRole()));
        }
        
        return new User(userName, password, authorities);
    }
    
}
