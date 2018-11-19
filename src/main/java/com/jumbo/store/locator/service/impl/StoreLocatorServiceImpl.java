package com.jumbo.store.locator.service.impl;

import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.TypeRef;
import com.jumbo.store.locator.domain.StoreInformation;
import com.jumbo.store.locator.service.api.StoreLocatorService;
import com.jumbo.store.locator.util.DistanceUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class StoreLocatorServiceImpl implements StoreLocatorService {

    private final Configuration configuration;//it is not used explicit, but used to configure as a bean
    private final DistanceUtil distanceUtil;
    private final List<StoreInformation> storeInformationList;


    private final Logger logger = LoggerFactory.getLogger(StoreLocatorServiceImpl.class);

    public StoreLocatorServiceImpl(Configuration configuration, DistanceUtil distanceUtil) {
        this.distanceUtil = distanceUtil;
        this.configuration = configuration;
        storeInformationList = retrieveData();
    }

    @Override
    public List<StoreInformation> getStoreInformationByCityName(double targetLong, double targetLat) {
        logger.info("finding nearest stores to the customer , inputs are , lang:{} and lat:{}", targetLong, targetLat);

        //using the idea of max heap algorithm
        createDistanceList(storeInformationList, targetLat, targetLong);
        List<StoreInformation> pocket = storeInformationList.stream().limit(5)
                .sorted(StoreInformation::compareTo).collect(Collectors.toList());

        for (int i = 5; i < storeInformationList.size(); i++) {
            if (pocket.get(4).getDistance() > storeInformationList.get(i).getDistance()) {
                pocket.set(4, storeInformationList.get(i));
                Collections.sort(pocket);
            }
        }
        return pocket;
    }

    private void createDistanceList(List<StoreInformation> storeInformationList,
                                    double targetLat, double targetLong) {
        for (StoreInformation store : storeInformationList) {
            store.setDistance(distanceUtil.calculate(targetLat, targetLong, store.getLatitude(), store.getLongitude()));
        }
    }

    //this method is going to be used once
    public List<StoreInformation> retrieveData() {
        String result;
        try {
            BufferedReader br = new BufferedReader(new FileReader("stores.json"));
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();
            while (line != null) {
                sb.append(line);
                line = br.readLine();
            }
            result = sb.toString();
            //setting json provide in order to use the typeref
            Object document = Configuration.defaultConfiguration().jsonProvider().parse(result);
            TypeRef<List<StoreInformation>> typeRef = new TypeRef<List<StoreInformation>>() {
            };
            //create a list of sotre information object based on the json
            return JsonPath.parse(document).read("$.stores", typeRef);
        } catch (Exception e) {
            logger.error("there was an error in reading file content , fileName is :{} and exception is:{}",
                    "stores.json", e);
            throw new RuntimeException("could not read the store file");
        }

    }
}
