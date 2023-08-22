package com.marcos.springsecurity.config;

import java.util.Collections;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.lang.Nullable;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.CsrfTokenRequestAttributeHandler;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import com.marcos.springsecurity.filter.CsrfCookieFilter;

import jakarta.servlet.http.HttpServletRequest;

@Configuration
@EnableWebSecurity
public class WebSecurity {

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        CsrfTokenRequestAttributeHandler requestHandler = new CsrfTokenRequestAttributeHandler();

        requestHandler.setCsrfRequestAttributeName("_csrf");

        http
                .securityContext(require -> require.requireExplicitSave(false))
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.ALWAYS))
                .cors(cors -> {
                    cors.configurationSource(new CorsConfigurationSource() {

                        @Override
                        @Nullable
                        public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
                            CorsConfiguration config = new CorsConfiguration();

                            config.setAllowedOrigins(Collections.singletonList("http://localhost:4200"));
                            config.setAllowedMethods(Collections.singletonList("*"));
                            config.setAllowCredentials(true);
                            config.setAllowedHeaders(Collections.singletonList("*"));
                            config.setMaxAge(3600L);
                            return config;
                        }

                    });

                    try {
                        http.csrf(csrf -> csrf.csrfTokenRequestHandler(requestHandler)
                                .ignoringRequestMatchers("/contact", "/register")
                                .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()))
                                .addFilterBefore(new CsrfCookieFilter(), BasicAuthenticationFilter.class)
                                .authorizeHttpRequests(auth -> auth.requestMatchers("/myAccount")
                                        .hasAuthority("VIEWACCOUNT")
                                        .requestMatchers("/myBalance").hasAnyAuthority("VIEWACCOUNT", "VIEWBALANCE")
                                        .requestMatchers("/myCards").hasAuthority("VIEWCARDS")
                                        .requestMatchers("/myLoans").hasAuthority("VIEWLOANS")
                                        .requestMatchers("/notices", "/contact", "/register").permitAll()
                                        .requestMatchers("/user").authenticated())
                                .formLogin(Customizer.withDefaults())
                                .httpBasic(Customizer.withDefaults());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                });
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
