package com.nnk.springboot.integration;

import com.nnk.springboot.configuration.SpringSecurityTestConfiguration;
import com.nnk.springboot.domain.RuleName;
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
        @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD,scripts = "classpath:RuleNameData.sql")
})
public class RuleNameTestIT {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithUserDetails("admin")
    @DisplayName("RuleNames Point Home")
    public void add_WhenCallRuleNameHome_MustBeOk() throws Exception {

        mockMvc.perform(get("/ruleName/list"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("rules"));
    }

    @Test
    @WithUserDetails("admin")
    @DisplayName("RuleName Add")
    public void add_WhenAddRuleName_MustHaveNewBid() throws Exception {

        RuleName ruleName = new RuleName();
        ruleName.setName("NAME RULE");
        ruleName.setDescription("RULE DESCRIPTION");
        ruleName.setJson("{\"field\":\"My JSON\"}");
        ruleName.setTemplate("RULE TEMPLATE");
        ruleName.setSqlStr("SELECT * FROM ruleName WHERE id= ?");
        ruleName.setSqlPart("25");

        mockMvc.perform(post("/ruleName/validate")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("name", ruleName.getName())
                .param("description", ruleName.getDescription())
                .param("json", ruleName.getJson())
                .param("template", ruleName.getTemplate())
                .param("sqlStr", ruleName.getSqlStr())
                .param("sqlPart", ruleName.getSqlPart())
                .with(csrf())
        )
                .andExpect(status().isFound());

        mockMvc.perform(get("/ruleName/list"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("rules"))
                .andExpect(model().attribute("rules",
                        hasItem(HasPropertyWithValue.hasProperty("name",is(ruleName.getName())))));
    }

    @Test
    @WithUserDetails("admin")
    @DisplayName("RuleName Update")
    public void add_WhenUpdateARuleName_MustBeFoundWithNewValues() throws Exception {

        mockMvc.perform(post("/ruleName/update/"+10)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("name", "NEW NAME")
                .param("description", "NEW DESCRIPTION")
                .param("json", "NEW JSON")
                .param("template", "NEW TEMPLATE")
                .param("sqlStr", "NEW SQL")
                .param("sqlPart", "NEW SQL PART")
                .with(csrf())
        ).andExpect(status().isFound());

        RuleName ruleName = new RuleName();
        ruleName.setId(10);

        mockMvc.perform(get("/ruleName/list"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("rules"))
                .andExpect(model().attribute("rules",
                        hasItem(ruleName)));

    }

    @Test
    @WithUserDetails("admin")
    @DisplayName("RuleName Delete")
    public void add_WhenDeleteRuleName_MustBeOk() throws Exception {

        mockMvc.perform(get("/ruleName/delete/"+11))
                .andExpect(status().isFound());

        mockMvc.perform(get("/ruleName/list"))
                .andExpect(model().attributeExists("rules"))
                .andExpect(model().attribute("rules",
                        not(hasItem(HasPropertyWithValue.hasProperty("id",is(11))))));
    }


}
