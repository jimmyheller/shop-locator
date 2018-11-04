package com.jumbo.store.locator.service.api;

import com.jumbo.store.locator.domain.StoreInformation;

import java.io.IOException;
import java.util.List;

public interface StoreLocationService {
    List<StoreInformation> getStoreInformationByCityName(String city);

    List<StoreInformation> getStoreInformationByCityName(String lang, String lat) throws IOException;
}
