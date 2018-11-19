package com.jumbo.store.locator;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jumbo.store.locator.domain.StoreInformation;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Created by a.azimipour on 11/19/2018.
 */
public class ScratchPad {


    public static void main(String[] args) throws IOException {
        new ScratchPad().run();
    }

    public void run() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();

        File file = new File("stores.json");


        List<StoreInformation> informationList= objectMapper.readValue(file, List.class);
        System.out.println(informationList);


    }
}

