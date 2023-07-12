package com.marcos.springsecurity.config;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class WebSecurity {

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(auth -> auth.requestMatchers("/notices", "/contact").permitAll()
                .requestMatchers("myAccount", "balance", "cards", "emprestimo").authenticated()
                .anyRequest().denyAll())
                .formLogin(Customizer.withDefaults())
                .httpBasic(Customizer.withDefaults());
        return http.build();
    }

    /*nesse bean estamos fazendo a autenticação via banco de dados
      essa autenticação tem que ser padrão do spring, então em minha tabela os nome dos atributos
      devem estar como o spring quer, tabela user, atributos username, password e enabled, com isso ele faz a autenticação
    */
    @Bean
    public UserDetailsService userDetailsService(DataSource dataSource){
        return new JdbcUserDetailsManager(dataSource);
    }
    

    //nesse bean estamos fazendo autenticação via memoria
    // @Bean
    // public InMemoryUserDetailsManager userDetails() {

    //     InMemoryUserDetailsManager inMemoryUserDetailsManager = new InMemoryUserDetailsManager();
    //     UserDetails user = User.withUsername("marcos")
    //             .password("{noop}admin")
    //             .authorities("USER")
    //             .build();

    //     UserDetails admin = User.withUsername("admin")
    //             .password("admin")
    //             .authorities("ADMIN")
    //             .build();

    //     inMemoryUserDetailsManager.createUser(user);
    //     inMemoryUserDetailsManager.createUser(admin);

    //     return inMemoryUserDetailsManager;
    // }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

}
