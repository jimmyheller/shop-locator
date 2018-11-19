package com.jumbo.store.locator.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jumbo.store.locator.domain.StoreInformation;
import com.jumbo.store.locator.service.api.StoreLocatorService;
import com.jumbo.store.locator.util.DistanceUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class StoreLocatorServiceImpl implements StoreLocatorService {

    private final DistanceUtil distanceUtil;
    private final List<StoreInformation> storeInformationList;


    private final Logger logger = LoggerFactory.getLogger(StoreLocatorServiceImpl.class);

    public StoreLocatorServiceImpl(DistanceUtil distanceUtil) {
        this.distanceUtil = distanceUtil;
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
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            File file = new File("stores.json");
            return objectMapper.readValue(file, new TypeReference<List<StoreInformation>>() {
            });
        } catch (Exception e) {
            logger.error("there was an error in reading file content , fileName is :{} and exception is:{}",
                    "stores.json", e);
            throw new RuntimeException("could not read the store file");
        }

    }
}
