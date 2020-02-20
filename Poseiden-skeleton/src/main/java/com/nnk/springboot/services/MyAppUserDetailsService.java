package com.nnk.springboot.services;

import com.nnk.springboot.repositories.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Optional;

@Service
@Slf4j
public class MyAppUserDetailsService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        return Optional.ofNullable(userRepository.findByUserName(userName))
                .map(user ->
                {
                    GrantedAuthority authority = new SimpleGrantedAuthority(user.getRole());
                    logger.info("User connected : " + userName + " role : " + authority);
                    return (UserDetails) new org.springframework.security.core.userdetails.User(user.getUsername(),
                            user.getPassword(), Collections.singletonList(authority));
                }).orElseThrow(() -> new UsernameNotFoundException("Your login/password is wrong or this account doesn't exist"));


    }


}
