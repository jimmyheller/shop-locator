package com.jumbo.store.locator.util;

import org.springframework.stereotype.Component;

/**
 * The source copyright is for stackOverflow.com , just customized enough to work for our purpose
 */
@Component
public class DistanceUtil {
    /**
     * calculate direct distance of two points by their coordinates
     *
     * @param lat1 target latitue
     * @param lon1 target longitude
     * @param lat2 latitude of desired point
     * @param lon2 longitude of desired point
     * @return
     */
    public double calculate(double lat1, double lon1, double lat2, double lon2) {
        double theta = lon1 - lon2;
        double dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2)) +
                Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta));

        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60 * 1.1515;
        dist = dist * 1.609344;

        return (dist);
    }


    /**
     * calculates radian value of decimal degree
     *
     * @param deg
     * @return radian value
     */
    private double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }

    /**
     * converts radians to decimal degree
     *
     * @param rad
     * @return deciaml value of the given radian
     */
    private double rad2deg(double rad) {
        return (rad * 180.0 / Math.PI);
    }

}
