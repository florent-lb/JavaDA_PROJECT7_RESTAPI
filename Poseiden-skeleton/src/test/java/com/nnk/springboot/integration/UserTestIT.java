package com.nnk.springboot.integration;

import com.nnk.springboot.configuration.SpringSecurityTestConfiguration;
import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.domain.User;
import org.hamcrest.beans.HasPropertyWithValue;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = {
        SpringSecurityTestConfiguration.class
})
@AutoConfigureMockMvc
@SqlGroup({
        @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD,scripts = "classpath:UserData.sql")
})
public class UserTestIT {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithUserDetails("admin")
    @DisplayName("User Home")
    public void add_WhenCallUser_MustBeOk() throws Exception {

        mockMvc.perform(get("/user/list"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("users"));
    }

    @Test
    @WithUserDetails("admin")
    @DisplayName("User Add")
    public void add_WhenAddUser_MustHaveNewBid() throws Exception {

        User user = new User();
        user.setPassword("test");
        user.setFullname("test");
        user.setRole("user");
        user.setUsername("test");

        mockMvc.perform(post("/user/validate")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("password", user.getPassword())
                .param("fullname", user.getFullname())
                .param("role", user.getRole())
                .param("username", user.getUsername())
                .with(csrf())
        )

                .andExpect(status().isFound());

        mockMvc.perform(get("/user/list"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("users"))
                .andExpect(model().attribute("users",
                        hasItem(HasPropertyWithValue.hasProperty("username",is(user.getUsername())))));
    }

    @Test
    @WithUserDetails("admin")
    @DisplayName("User Update")
    public void add_WhenUpdateABid_MustBeFoundWithNewValues() throws Exception {

        mockMvc.perform(post("/user/update/"+10)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("password", "NEW_PASSWORD")
                .param("fullname", "NEW_FULLNAME")
                .param("role", "NEW_ROLE")
                .param("username", "NEW_USERNAME")
                .with(csrf())
        ).andExpect(status().isFound());

        User user = new User();
        user.setId(10);

        mockMvc.perform(get("/user/list"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("users"))
                .andExpect(model().attribute("users",
                        hasItem(user)));

    }

    @Test
    @WithUserDetails("admin")
    @DisplayName("User Delete")
    public void add_WhenDeleteBid_MustBeOk() throws Exception {

        mockMvc.perform(get("/user/delete/"+11))
                .andExpect(status().isFound());

        mockMvc.perform(get("/user/list"))
                .andExpect(model().attributeExists("users"))
                .andExpect(model().attribute("users",
                        not(hasItem(HasPropertyWithValue.hasProperty("id",is(11))))));
    }


}
