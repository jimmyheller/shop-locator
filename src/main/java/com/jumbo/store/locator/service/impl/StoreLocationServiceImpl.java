package com.jumbo.store.locator.service.impl;

import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.Option;
import com.jayway.jsonpath.TypeRef;
import com.jayway.jsonpath.spi.json.JacksonJsonProvider;
import com.jayway.jsonpath.spi.json.JsonProvider;
import com.jayway.jsonpath.spi.mapper.JacksonMappingProvider;
import com.jayway.jsonpath.spi.mapper.MappingProvider;
import com.jumbo.store.locator.LatLong;
import com.jumbo.store.locator.domain.StoreInformation;
import com.jumbo.store.locator.service.api.StoreLocationService;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

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
    public double[] getStoreInformationByCityName(String lang, String lat) throws IOException {


        Configuration.setDefaults(new Configuration.Defaults() {

            private final JsonProvider jsonProvider = new JacksonJsonProvider();
            private final MappingProvider mappingProvider = new JacksonMappingProvider();

            @Override
            public JsonProvider jsonProvider() {
                return jsonProvider;
            }

            @Override
            public MappingProvider mappingProvider() {
                return mappingProvider;
            }

            @Override
            public Set<Option> options() {
                return EnumSet.noneOf(Option.class);
            }
        });

        String json = readFile("stores.json");
        Object document = Configuration.defaultConfiguration().jsonProvider().parse(json);
        TypeRef<List<StoreInformation>> typeRef = new TypeRef<List<StoreInformation>>() {
        };


        List<StoreInformation> storeInformations = JsonPath.parse(document).read("$.stores", typeRef);
        List<double[]> coordinates = new ArrayList<>();
        for (StoreInformation store : storeInformations) {
            double[] n = LatLong.toEcef(new double[]{Double.parseDouble(store.getLatitude()), Double.parseDouble(store.getLongitude())});
            coordinates.add(n);
        }

        double[] targetv = LatLong.toEcef(new double[]{Double.parseDouble(lat), Double.parseDouble(lang)});

        int i = LatLong.closest(targetv, coordinates);


        double[] closest = coordinates.get(i);


        return closest;
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
