package com.nnk.springboot.configuration;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import java.util.List;

@TestConfiguration
public class SpringSecurityTestConfiguration {


    @Bean
    @Primary
    public UserDetailsService userDetailsService()
    {
        User basicUser = new User("user","password", List.of(new SimpleGrantedAuthority("USER")));

        User adminUser = new User("admin","password", List.of(new SimpleGrantedAuthority("ADMIN"),new SimpleGrantedAuthority("USER")));

        return new InMemoryUserDetailsManager(List.of(basicUser,adminUser));
    }

}
