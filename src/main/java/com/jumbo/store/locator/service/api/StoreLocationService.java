package com.jumbo.store.locator.service.api;

import com.jumbo.store.locator.domain.StoreInformation;

import java.util.List;

public interface StoreLocationService {

    /**
     * by giving coordinates of the point this method would calculate 5 points which is the nearest by direct
     * distance.
     * @param targetLong
     * @param targetLat
     * @return list of 5 nearest point to the given location
     */
    List<StoreInformation> getStoreInformationByCityName(double targetLong, double targetLat);
}
