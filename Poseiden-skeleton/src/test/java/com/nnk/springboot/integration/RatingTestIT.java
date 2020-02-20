package com.nnk.springboot.integration;

import com.nnk.springboot.configuration.SpringSecurityTestConfiguration;
import com.nnk.springboot.domain.Rating;
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
        @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD,scripts = "classpath:RatingData.sql")
})
public class RatingTestIT {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithUserDetails("admin")
    @DisplayName("Ratings Point Home")
    public void add_WhenCallRatingHome_MustBeOk() throws Exception {

        mockMvc.perform(get("/rating/list"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("ratings"));
    }

    @Test
    @WithUserDetails("admin")
    @DisplayName("Rating Add")
    public void add_WhenAddRating_MustHaveNewBid() throws Exception {

        Rating rating = new Rating();
        rating.setMoodysRating("UNIQUE_MODY_BAD");
        rating.setSandPRating("FOO");
        rating.setFitchRating("FOOFOO");
        rating.setOrderNumber("5");

        mockMvc.perform(post("/rating/validate")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("moodysRating", rating.getMoodysRating())
                .param("sandPRating", rating.getSandPRating())
                .param("fitchRating", rating.getFitchRating())
                .param("orderNumber", rating.getOrderNumber())
                .with(csrf())
        )

                .andExpect(status().isFound());

        mockMvc.perform(get("/rating/list"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("ratings"))
                .andExpect(model().attribute("ratings",
                        hasItem(HasPropertyWithValue.hasProperty("moodysRating",is(rating.getMoodysRating())))));
    }

    @Test
    @WithUserDetails("admin")
    @DisplayName("Rating Update")
    public void add_WhenUpdateARating_MustBeFoundWithNewValues() throws Exception {

        mockMvc.perform(post("/rating/update/"+10)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("moodysRating", "GOOD")
                .param("sandPRating", "OOF")
                .param("fitchRating", "OOFOOF")
                .param("orderNumber", "50")
                .with(csrf())
        ).andExpect(status().isFound());

        Rating rating = new Rating();
        rating.setId(10);

        mockMvc.perform(get("/rating/list"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("ratings"))
                .andExpect(model().attribute("ratings",
                        hasItem(rating)));

    }

    @Test
    @WithUserDetails("admin")
    @DisplayName("Rating Delete")
    public void add_WhenDeleteRating_MustBeOk() throws Exception {

        mockMvc.perform(get("/rating/delete/"+11))
                .andExpect(status().isFound());

        mockMvc.perform(get("/rating/list"))
                .andExpect(model().attributeExists("ratings"))
                .andExpect(model().attribute("ratings",
                        not(hasItem(HasPropertyWithValue.hasProperty("id",is(11))))));
    }


}
