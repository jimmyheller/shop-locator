package com.jumbo.store.locator;

import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.Option;
import com.jayway.jsonpath.TypeRef;
import com.jayway.jsonpath.spi.json.JacksonJsonProvider;
import com.jayway.jsonpath.spi.json.JsonProvider;
import com.jayway.jsonpath.spi.mapper.JacksonMappingProvider;
import com.jayway.jsonpath.spi.mapper.MappingProvider;
import com.jumbo.store.locator.domain.StoreInformation;
import sun.security.krb5.Config;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.EnumSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class ScratchPad {


    public static void main(String[] args) {
        new ScratchPad().run();
    }

    private void run() {
        double targetLat = 52.2225196;
        double targetLong = 5.1646021;

        String json = readFile("stores.json"); // todo : make it some where static and on runtime
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

        Object document = Configuration.defaultConfiguration().jsonProvider().parse(json);
        TypeRef<List<StoreInformation>> typeRef = new TypeRef<List<StoreInformation>>() {
        };

        //create a list of sotre information object based on the json
        List<StoreInformation> storeInformationList = JsonPath.parse(document).read("$.stores", typeRef);


        //creating a pocket of top 5 stores , since the list is sorted by city , the chance of finding the closest is going to be increased
        List<StoreInformation> top5Stores = new LinkedList<>();
        top5Stores.add(storeInformationList.get(0));
        top5Stores.add(storeInformationList.get(1));
        top5Stores.add(storeInformationList.get(2));
        top5Stores.add(storeInformationList.get(3));
        top5Stores.add(storeInformationList.get(4));

        //calculate the distance of the top5 points from the list
        double farDistance = 0;
        int mostDistantPointIndex = 0;

        //getMax of the pocket
        for (int i = 0; i < top5Stores.size(); i++) {
            System.out.println("traversing index : " + i);
            double currentNodeDistance = distance(targetLat, targetLong, top5Stores.get(i).getLatitude(), top5Stores.get(i).getLongitude());
            System.out.println("distance to current node from lat:" + top5Stores.get(i).getLatitude() + " long: " + top5Stores.get(i).getLongitude() + " is : " + currentNodeDistance);
            if (currentNodeDistance > farDistance) {
                System.out.println("therefore we changed the index of the current point of max!");
                mostDistantPointIndex = i;
                farDistance = currentNodeDistance;
            }
        }
        System.out.println("most distant point index is :" + mostDistantPointIndex);

        System.out.println("starting to check the other fields of the list in the store information");

        for (int i = 5; i < storeInformationList.size(); i++) {
            double currentNodeDistance = distance(targetLat, targetLong, storeInformationList.get(i).getLatitude(), storeInformationList.get(i).getLongitude());
            System.out.println("distance to current node from lat:" + storeInformationList.get(i).getLatitude() + " long: " + storeInformationList.get(i).getLongitude() + " is : " + currentNodeDistance);
            if (farDistance > currentNodeDistance) {
                System.out.println("far distance is less than the current node so change and add to the top5");
                top5Stores.set(mostDistantPointIndex, storeInformationList.get(i));
                farDistance = currentNodeDistance;
            }
        }

        System.out.println("found all the solution ");
        for (StoreInformation j : top5Stores) {
            System.out.println("lat and lang : " + j.getLatitude() + "," + j.getLongitude());
            System.out.println(distance(targetLat, targetLong, j.getLatitude(), j.getLongitude()));
        }
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
    private static double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }

    /*:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::*/
    /*::  This function converts radians to decimal degrees             :*/
    /*:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::*/
    private static double rad2deg(double rad) {
        return (rad * 180.0 / Math.PI);
    }

//    public static void main2(String[] args) {
//        //System.out.println(distance(32.9697, -96.80322, 29.46786, -98.53506, 'M') + " Miles\n");
//        System.out.println(distance(52.429240, 5.879367, 51.778461, 4.615551, 'K') + " Kilometers\n");
//        //System.out.println(distance(32.9697, -96.80322, 29.46786, -98.53506, 'N') + " Nautical Miles\n");
//    }

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
