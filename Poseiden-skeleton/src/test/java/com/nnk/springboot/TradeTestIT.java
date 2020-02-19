package com.nnk.springboot;

import com.nnk.springboot.configuration.SpringSecurityTestConfiguration;
import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.domain.Trade;
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
        @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD,scripts = "classpath:TradeData.sql")
})
public class TradeTestIT {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithUserDetails("admin")
    @DisplayName("Trade Home")
    public void add_WhenCallTradeHome_MustBeOk() throws Exception {

        mockMvc.perform(get("/trade/list"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("trades"));
    }

    @Test
    @WithUserDetails("admin")
    @DisplayName("Trade Add")
    public void add_WhenAddTrade_MustHaveNewBid() throws Exception {

        Trade trade = new Trade();
        trade.setAccount("ACCOUNT UNIQUE!!");
        trade.setType("TYPE");
        trade.setBuyQuantity(10.0);

        mockMvc.perform(post("/trade/validate")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("account", trade.getAccount())
                .param("type", trade.getType())
                .param("buyQuantity", trade.getBuyQuantity().toString())
                .with(csrf())
        )

                .andExpect(status().isFound());

        mockMvc.perform(get("/trade/list"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("trades"))
                .andExpect(model().attribute("trades",
                        hasItem(HasPropertyWithValue.hasProperty("account",is(trade.getAccount())))));
    }

    @Test
    @WithUserDetails("admin")
    @DisplayName("Trade Update")
    public void add_WhenUpdateABid_MustBeFoundWithNewValues() throws Exception {

        mockMvc.perform(post("/trade/update/"+10)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("account", "NEW_ACCOUNT")
                .param("type", "NEW_TYPE")
                .param("buyQuantity", "103.3")
                .with(csrf())
        ).andExpect(status().isFound());

        Trade trade = new Trade();
        trade.setTradeId(10);

        mockMvc.perform(get("/trade/list"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("trades"))
                .andExpect(model().attribute("trades",
                        hasItem(trade)));

    }

    @Test
    @WithUserDetails("admin")
    @DisplayName("Trade Delete")
    public void add_WhenDeleteBid_MustBeOk() throws Exception {

        mockMvc.perform(get("/trade/delete/"+11))
                .andExpect(status().isFound());

        mockMvc.perform(get("/trade/list"))
                .andExpect(model().attributeExists("trades"))
                .andExpect(model().attribute("trades",
                        not(hasItem(HasPropertyWithValue.hasProperty("tradeId",is(11))))));
    }


}
