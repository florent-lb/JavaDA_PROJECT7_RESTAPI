package com.nnk.springboot;

import com.nnk.springboot.configuration.SpringSecurityTestConfiguration;
import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.domain.CurvePoint;
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
        @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD,scripts = "classpath:CurvePointData.sql")
})
public class CurvePointTestIT {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithUserDetails("admin")
    @DisplayName("Curves Point Home")
    public void add_WhenCallCurvePointHome_MustBeOk() throws Exception {

        mockMvc.perform(get("/curvePoint/list"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("curves"));
    }

    @Test
    @WithUserDetails("admin")
    @DisplayName("Curve point Add")
    public void add_WhenAddCurvePoint_MustHaveNewBid() throws Exception {

        CurvePoint curve = new CurvePoint();
        curve.setCurveId((short)1);
        curve.setTerm(0.5);
        curve.setValue(1.5);

        mockMvc.perform(post("/curvePoint/validate")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("curveId", curve.getCurveId().toString())
                .param("term", curve.getTerm().toString())
                .param("value", curve.getValue().toString())
                .with(csrf())
        )

                .andExpect(status().isFound());

        mockMvc.perform(get("/curvePoint/list"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("curves"))
                .andExpect(model().attribute("curves",
                        hasItem(HasPropertyWithValue.hasProperty("curveId",is(curve.getCurveId())))));
    }

    @Test
    @WithUserDetails("admin")
    @DisplayName("Curve Point Update")
    public void add_WhenUpdateACurvePoint_MustBeFoundWithNewValues() throws Exception {

        mockMvc.perform(post("/curvePoint/update/"+10)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("curveId", "100")
                .param("term", "25.0")
                .param("value", "35.0")
                .with(csrf())
        ).andExpect(status().isFound());

        CurvePoint curve = new CurvePoint();
        curve.setId(10);

        mockMvc.perform(get("/curvePoint/list"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("curves"))
                .andExpect(model().attribute("curves",
                        hasItem(curve)));

    }

    @Test
    @WithUserDetails("admin")
    @DisplayName("Curve Point Delete")
    public void add_WhenDeleteCurve_MustBeOk() throws Exception {

        mockMvc.perform(get("/curvePoint/delete/"+11))
                .andExpect(status().isFound());

        mockMvc.perform(get("/curvePoint/list"))
                .andExpect(model().attributeExists("curves"))
                .andExpect(model().attribute("curves",
                        not(hasItem(HasPropertyWithValue.hasProperty("id",is(11))))));
    }


}
