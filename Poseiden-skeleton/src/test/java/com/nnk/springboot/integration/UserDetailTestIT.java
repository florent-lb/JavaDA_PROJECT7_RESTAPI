package com.nnk.springboot.integration;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.repositories.UserRepository;
import com.nnk.springboot.services.MyAppUserDetailsService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import static org.mockito.Mockito.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserDetailTestIT {


    @Autowired
    private MyAppUserDetailsService userDetailsService;

    @MockBean
    private UserRepository userRepository;

    @Test
    @DisplayName("Test Load user")
    public void loadUser_whenAskWithUserName_shouldReturnValidUserDetail() {
        String userName = "test";
        User user = new User();
        user.setPassword("test");
        user.setFullname("test");
        user.setRole("user");
        user.setUsername(userName);
        when(userRepository.findByUserName(anyString())).thenReturn(user);

        UserDetails details = userDetailsService.loadUserByUsername(userName);

        Assertions.assertThat(details.getUsername())
                .isEqualTo(userName);

        Assertions.assertThat(details.getAuthorities())
                .extracting(GrantedAuthority::getAuthority)
                .contains(user.getRole());


    }

}
