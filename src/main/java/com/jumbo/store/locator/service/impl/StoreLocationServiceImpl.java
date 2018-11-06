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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

@Service
public class StoreLocationServiceImpl implements StoreLocationService {

    @Autowired
    Configuration configuration;

    @Override
    public double[] getStoreInformationByCityName(String lang, String lat) throws IOException {
        double targetLatitude = Double.parseDouble(lat);
        double targetLongitude = Double.parseDouble(lang);

        //read json file and create the document object
        String json = readFile("stores.json"); // todo : make it some where static and on runtime
        Object document = configuration.jsonProvider().parse(json);
        TypeRef<List<StoreInformation>> typeRef = new TypeRef<List<StoreInformation>>() {
        };

        //create a list of sotre information object based on the json
        List<StoreInformation> storeInformationList = JsonPath.parse(document).read("$.stores", typeRef);

        double[] targetv = LatLong.toEcef(new double[]{Double.parseDouble(lat), Double.parseDouble(lang)});

        //creating a pocket of top 5 stores , since the list is sorted by city , the chance of finding the closest is going to be increased
        List<StoreInformation> top5Stores = new LinkedList<>();
        top5Stores.add(storeInformationList.get(0));
        top5Stores.add(storeInformationList.get(1));
        top5Stores.add(storeInformationList.get(2));
        top5Stores.add(storeInformationList.get(3));
        top5Stores.add(storeInformationList.get(4));

        //calculate the distance of the top5 points from the list
        double farDistance = 0;//distance(targetLatitude, targetLongitude, top5Stores.get(0).getLatitude(), top5Stores.get(0).getLongitude());
        int mostDistantPointIndex = 0;

        for (int i =0; i < top5Stores.size(); i++) {
            double currentNodeDistance = distance(targetLatitude, targetLongitude, top5Stores.get(i).getLatitude(), top5Stores.get(i).getLongitude());
            if (currentNodeDistance > farDistance) {
                mostDistantPointIndex = i;
            }
        }

//        for (int i = 5; i < storeInformationList.size(); i++) {
//            double currentNodeDistance = distance(targetLatitude, targetLongitude, storeInformationList.get(i).getLatitude(), storeInformationList.get(i).getLatitude());
//            if (currentNodeDistance > farDistance) {
//                mostDistantPointIndex = i;
//            }
//        }

        //traversing throught list of the top5 and find the last point in case of distance
        //todo : use collection api
        return null;
    }

    private double distance(double lat1, double lon1, double lat2, double lon2) {
        double theta = lon1 - lon2;
        double dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2)) + Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta));
        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60 * 1.1515;
        dist = dist * 1.609344;

        return (dist);
    }

    /*:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::*/
    /*::  This function converts decimal degrees to radians             :*/
    /*:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::*/
    private double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }

    /*:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::*/
    /*::  This function converts radians to decimal degrees             :*/
    /*:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::*/
    private double rad2deg(double rad) {
        return (rad * 180.0 / Math.PI);
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
