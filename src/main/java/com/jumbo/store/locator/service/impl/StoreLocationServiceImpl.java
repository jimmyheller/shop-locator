package com.jumbo.store.locator.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.JsonPath;
import com.jumbo.store.locator.domain.StoreInformation;
import com.jumbo.store.locator.service.api.StoreLocationService;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class StoreLocationServiceImpl implements StoreLocationService {
    @Override
    public List<StoreInformation> getStoreInformationByCityName(String city) {
        String json = readFile("stores.json");
        Object document = Configuration.defaultConfiguration().jsonProvider().parse(json);
        String author0 = JsonPath.read(document, "$.stores[0].city");
        return null;
    }

    @Override
    public List<StoreInformation> getStoreInformationByCityName(String lang, String lat) throws IOException {
//        String json = readFile("stores.json");
//        Object document = Configuration.defaultConfiguration().jsonProvider().parse(json);
//        String author0 = JsonPath.read(docum'ent, "$.stores[0].city");
        Map<String, Object> stringObjectMap = new ObjectMapper().readValue(new File("stores.json"), HashMap.class);
        stringObjectMap.get("stores");
        return null;

    }

    public static String readFile(String filename) {
        String result = "";
        try {
            BufferedReader br = new BufferedReader(new FileReader(filename));
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();
            while (line != null) {
                sb.append(line);
                line = br.readLine();
            }
            result = sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}
