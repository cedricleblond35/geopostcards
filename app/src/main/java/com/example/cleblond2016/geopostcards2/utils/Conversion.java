package com.example.cleblond2016.geopostcards2.utils;

public class Conversion {

    public enum Unit {
        UNIT_K,
        UNIT_M
    }

    /**
     * Calcul de distance entre deux sur la terre
     * @param lat1 latitude du premier point
     * @param lon1 longitude du premier point
     * @param lat2 latitude du premier deuxième
     * @param lon2 longitude du premier deuxième
     * @param unit unité en kilomètre ou milles
     * @return distance
     */
    public static double calculateDistance(double lat1, double lon1, double lat2, double lon2, Unit unit) {
        double radlat1 = Math.PI * lat1 / 180;
        double radlat2 = Math.PI * lat2 / 180;
        double theta = lon1 - lon2;
        double radtheta = Math.PI * theta / 180;
        double dist = Math.sin(radlat1) * Math.sin(radlat2) + Math.cos(radlat1) * Math.cos(radlat2) * Math.cos(radtheta);
        dist = Math.acos(dist);
        dist = dist * 180 / Math.PI;
        dist = dist * 60 * 1.1515;
        if (unit == Unit.UNIT_K) {
            dist = dist * 1.609344;
        }
        if (unit == Unit.UNIT_M) {
            dist = dist * 0.8684;
        }
        return dist;
    }
}
