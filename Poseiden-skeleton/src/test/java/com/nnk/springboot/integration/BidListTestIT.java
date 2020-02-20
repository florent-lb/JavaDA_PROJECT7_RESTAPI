package com.nnk.springboot.integration;

import com.nnk.springboot.configuration.SpringSecurityTestConfiguration;
import com.nnk.springboot.domain.BidList;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(classes = {
        SpringSecurityTestConfiguration.class
})
@AutoConfigureMockMvc
@SqlGroup({
        @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD,scripts = "classpath:BidListData.sql")
})
public class BidListTestIT {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithUserDetails("admin")
    @DisplayName("Bid List Home")
    public void add_WhenCallBidListHome_MustBeOk() throws Exception {

        mockMvc.perform(get("/bidList/list"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("bids"));
    }

    @Test
    @WithUserDetails("admin")
    @DisplayName("Bid List Add")
    public void add_WhenAddBidList_MustHaveNewBid() throws Exception {

        BidList bid = new BidList("TESTACC", "TESTT", 10.3);

        mockMvc.perform(post("/bidList/validate")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("account", bid.getAccount())
                .param("type", bid.getType())
                .param("bidQuantity", bid.getBidQuantity().toString())
                .with(csrf())
        )

                .andExpect(status().isFound());

        mockMvc.perform(get("/bidList/list"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("bids"))
                .andExpect(model().attribute("bids",
                        hasItem(HasPropertyWithValue.hasProperty("account",is(bid.getAccount())))));
    }

    @Test
    @WithUserDetails("admin")
    @DisplayName("Bid List Update")
    public void add_WhenUpdateABid_MustBeFoundWithNewValues() throws Exception {

        mockMvc.perform(post("/bidList/update/"+10)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("account", "NEW_ACCOUNT")
                .param("type", "NEW_TYPE")
                .param("bidQuantity", "50.5")
                .with(csrf())
        ).andExpect(status().isFound());

        BidList bid = new BidList("NEW_ACCOUNT","NEW_TYPE",50.5);
        bid.setBidListId(10);

        mockMvc.perform(get("/bidList/list"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("bids"))
                .andExpect(model().attribute("bids",
                        hasItem(bid)));

    }

    @Test
    @WithUserDetails("admin")
    @DisplayName("Bid List Delete")
    public void add_WhenDeleteBid_MustBeOk() throws Exception {

        mockMvc.perform(get("/bidList/delete/"+11))
                .andExpect(status().isFound());

        mockMvc.perform(get("/bidList/list"))
                .andExpect(model().attributeExists("bids"))
                .andExpect(model().attribute("bids",
                        not(hasItem(HasPropertyWithValue.hasProperty("bidListId",is(11))))));
    }


}
