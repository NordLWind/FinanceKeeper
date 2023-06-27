package ru.kostin.financekeeper.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.Collections;

@Configuration
public class MockSecurityConfiguration {

    @Bean
    UserDetailsService mockUserDetailsService() {
        return username -> new CustomUserDetails(
               1L,
               "test@t",
               "password",
               Collections.singleton(new CustomGrantedAuthority(Role.ADMIN))
       );
    }
}
