package com.jumbo.store.locator.web.controller;

import com.jumbo.store.locator.domain.StoreInformation;
import com.jumbo.store.locator.web.object.StoreLocatorResponse;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
public class StoreLocatorControllerIntTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;


    @Test
    public void integrationTest() throws Exception {
        StoreLocatorResponse<List<StoreInformation>> sampleList = new StoreLocatorResponse<>();


        assertThat(this.restTemplate.getForObject("http://localhost:" + port +
                "/api/v1/stores/coordinates?lat=52.924788&long=5.737673", StoreLocatorResponse.class)).
                isEqualToIgnoringGivenFields(sampleList.getData().get(0).getCity());
    }

}
