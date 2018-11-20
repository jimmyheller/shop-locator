package com.jumbo.store.locator;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jumbo.store.locator.domain.StoreInformation;
import com.jumbo.store.locator.web.object.StoreLocatorResponse;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static com.jumbo.store.locator.web.object.StoreLocatorResponse.SUCCESSFUL;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
public class ApplicationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    List<StoreInformation> sampleList;


    //read the sample file which stores the correct list of stores for a specific point
    @Before
    public void setUp() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        File file = new File("sample-response-for-test.json");
        sampleList = objectMapper.readValue(file, new TypeReference<List<StoreInformation>>() {
        });

    }

    //fully test zero to 100 a successful response,since the application does not have a repository object , we use this
    // as a service test too
    @Test
    public void integrationTest() throws Exception {
        StoreLocatorResponse<List<StoreInformation>> sampleResponse = new StoreLocatorResponse<>();

        sampleResponse.setData(sampleList);
        sampleResponse.setResponseCode(SUCCESSFUL);

        //valid location near Sint Nicolaasga, this place has been chosen by chance in google map
        String response = this.restTemplate.getForObject("http://localhost:" +
                port + "/api/v1/stores/coordinates?lat=52.924788&long=5.737673", String.class);
        ObjectMapper mapper = new ObjectMapper();
        StoreLocatorResponse<List<StoreInformation>> stores =
                mapper.readValue(response, new TypeReference<StoreLocatorResponse<List<StoreInformation>>>() {
                });
        List<StoreInformation> data = stores.getData();
        for (int i = 0; i < data.size(); i++) { //the order of objects is important
            //in tdd we are not going to have the distance property before writing the algorithm
            assertThat(data.get(i)).isEqualToIgnoringGivenFields(sampleList.get(i), "distance");
        }
        assertThat(stores.getResponseCode()).isEqualTo(sampleResponse.getResponseCode());
    }

}
