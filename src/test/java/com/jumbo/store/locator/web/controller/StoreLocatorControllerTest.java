package com.jumbo.store.locator.web.controller;

import com.jumbo.store.locator.service.api.StoreLocatorService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static com.jumbo.store.locator.web.object.StoreLocatorResponse.NOT_VALID_LATITUDE;
import static com.jumbo.store.locator.web.object.StoreLocatorResponse.NOT_VALID_LONGITUDE;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@WebMvcTest
public class StoreLocatorControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    StoreLocatorService service;

    @Test
    public void testControllerWithoutInputs() throws Exception {
        this.mvc.perform(get("/api/v1/stores/coordinates").
                accept(MediaType.APPLICATION_JSON)).andExpect(status().is4xxClientError());
    }

    @Test
    public void testLatValueOutOfRangeMax() throws Exception {
        this.mvc.perform(get("/api/v1/stores/coordinates?lat=100&long=80"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("responseCode").value(NOT_VALID_LATITUDE));
    }

    @Test
    public void testLatValueOutOfRangeMin() throws Exception {
        this.mvc.perform(get("/api/v1/stores/coordinates?lat=-91&long=80"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("responseCode").value(NOT_VALID_LATITUDE));
    }

    @Test
    public void testLongValueOutOfRangeMax() throws Exception {
        this.mvc.perform(get("/api/v1/stores/coordinates?lat=71&long=185"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("responseCode").value(NOT_VALID_LONGITUDE));
    }

    @Test
    public void testLongValueOutOfRangeMin() throws Exception {
        this.mvc.perform(get("/api/v1/stores/coordinates?lat=71&long=-185"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("responseCode").value(NOT_VALID_LONGITUDE));
    }

    @Test
    public void testLongValuIsCharacter() throws Exception {
        this.mvc.perform(get("/api/v1/stores/coordinates?lat=www&long=7"))
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void testLatValuIsCharacter() throws Exception {
        this.mvc.perform(get("/api/v1/stores/coordinates?lat=44&long=wss"))
                .andExpect(status().is4xxClientError());
    }


}