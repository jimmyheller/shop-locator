package com.jumbo.store.locator.web.controller;

import com.jumbo.store.locator.domain.StoreInformation;
import com.jumbo.store.locator.service.api.StoreLocationService;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping(path = "/api/v1/stores/location")
public class StoreLocationController {


    private StoreLocationService service;

    public StoreLocationController(StoreLocationService service) {
        this.service = service;
    }

    //<editor-fold desc="get methods">

    @GetMapping(path = {"/lang/{lang}/lat/{lat}", "/lat/{lat}/lang/{lang}"})
    public @ResponseBody
    double[] getStoreInformation(@PathVariable("lang") String lang, @PathVariable("lat") String lat) {
        try {
            return service.getStoreInformationByCityName(lang, lat);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    //</editor-fold>

    //<editor-fold desc="post methods">


    //</editor-fold>
}
