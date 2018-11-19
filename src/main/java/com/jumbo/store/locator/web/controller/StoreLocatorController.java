package com.jumbo.store.locator.web.controller;

import com.jumbo.store.locator.domain.StoreInformation;
import com.jumbo.store.locator.service.api.StoreLocatorService;
import com.jumbo.store.locator.web.object.StoreLocatorResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

import static com.jumbo.store.locator.web.object.StoreLocatorResponse.*;

@RestController
@RequestMapping(path = "/api/v1/stores/coordinates")
public class StoreLocatorController {
    //<editor-fold desc="variables and constructor">
    private static final Logger logger = LoggerFactory.getLogger(StoreLocatorController.class);
    private StoreLocatorService service;

    public StoreLocatorController(StoreLocatorService service) {
        this.service = service;
    }
    //</editor-fold>

    //<editor-fold desc="get methods">
    @GetMapping
    public @ResponseBody
    StoreLocatorResponse<List<StoreInformation>> getStoreInformation(@RequestParam("long") double targetLong,
                                                                     @RequestParam("lat") double targetLat,
                                                                     HttpServletRequest request) {
        StoreLocatorResponse<List<StoreInformation>> response = new StoreLocatorResponse<>();
        try {
            logger.info("incoming request form: {}, with inputs of longitude: {} and latitude: {}",
                    request.getRemoteAddr(), targetLong, targetLat);

            if (targetLat > 90 || targetLat < -90) {
                response.setResponseCode(NOT_VALID_LATITUDE);
                response.setMessage("your latitude is not valid");
                return response;
            }
            if (targetLong > 180 || targetLong < -180) {
                response.setResponseCode(NOT_VALID_LONGITUDE);
                response.setMessage("your latitude is not valid");
                return response;
            }
            List<StoreInformation> data = service.getStoreInformationByCityName(targetLong, targetLat);

            //since we are not checking that how far it is going to be and assuming that jumbo has at least 5 store near
            //every customer the list is not going to be null or empty
            response.setData(data);
            response.setResponseCode(SUCCESSFUL);
            response.setMessage("operation was successful");
            logger.info("operation was successful for request with inputs of longitude: {} and latitude: {} " +
                    "and returned:{}", targetLong, targetLat, data);

        } catch (Exception q) {
            response.setMessage("there was an error in finding the requested data");
            response.setResponseCode(UNSUCCESSFUL);
            logger.error("error in getStoreInformation() ", q);
        }
        return response;
    }
    //</editor-fold>

    //<editor-fold desc="post methods">
    //</editor-fold>
}
