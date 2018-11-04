package com.jumbo.store.locator;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.lang.Math.*;

public class LatLong {
    public static void main(String[] args) throws Exception {
        // A random lat/long
        double[] target = randLatLong();
        // Transform to ECEF vector
        double[] targetv = toEcef(target);
        // 2000 random candidates
        List<double[]> b = Stream.generate(LatLong::randLatLong).limit(2000).collect(Collectors.toList());
        // Transform candidates to ECEF representation
        List<double[]> bv = b.stream().map(LatLong::toEcef).collect(Collectors.toList());

        // Find the closest candidate to the target
        int i = closest(targetv, bv);

        System.out.println("Closest point to " + target[0] + ", " + target[1] + " is " + b.get(i)[0] + ", " + b.get(i)[1]);
    }

    // index of closest vector to target from list of candidates
    public static int closest(double[] target, List<double[]> candidates) {
        double p = Double.MIN_VALUE;
        int closest = -1;
        for (int i = 0; i < candidates.size(); i++) {
            double next = dotProduct(target, candidates.get(i));
            if (next > p) {
                p = next;
                closest = i;
            }
        }
        return closest;
    }

    // dot product of two 3vectors
    public static double dotProduct(double[] v1, double[] v2) {
        return v1[0] * v2[0] + v1[1] * v2[1] + v1[2] * v2[2];
    }

    // lat/long in degrees to normalised ECEF vector
    public static double[] toEcef(double[] latLong) {
        return toEcef(toRadians(latLong[0]), toRadians(latLong[1]));
    }

    // lat/long in radians to normalised ECEF vector
    public static double[] toEcef(double φ, double λ) {
        return new double[] {cos(φ) * cos(λ), cos(φ) * sin(λ), sin(φ)};
    }

    // A random lat/long
    public static double[] randLatLong() {
        return new double[] {Math.random() * 180 - 90, Math.random() * 360 - 180};
    }
}