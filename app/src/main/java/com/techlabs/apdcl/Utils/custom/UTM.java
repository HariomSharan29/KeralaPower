package com.techlabs.apdcl.Utils.custom;


public class UTM {

    public int zone;
    public String hemisphere;
    public double easting;
    public double northing;

    public UTM(int zone, String hemisphere, double easting, double northing) {
        this.zone = zone;
        this.hemisphere = hemisphere;
        this.easting = easting;
        this.northing = northing;
    }

    public static UTM latLonToUTM(double lat, double lon) {

        int zone = (int) Math.floor((lon + 180) / 6) + 1;
        String hemisphere = lat >= 0 ? "N" : "S";

        double latRad = Math.toRadians(lat);
        double lonRad = Math.toRadians(lon);

        double lonOrigin = (zone - 1) * 6 - 180 + 3;
        double lonOriginRad = Math.toRadians(lonOrigin);

        double a = 6378137.0;
        double eccSquared = 0.00669438;
        double k0 = 0.9996;

        double eccPrimeSquared = eccSquared / (1 - eccSquared);

        double N = a / Math.sqrt(1 - eccSquared * Math.sin(latRad) * Math.sin(latRad));
        double T = Math.tan(latRad) * Math.tan(latRad);
        double C = eccPrimeSquared * Math.cos(latRad) * Math.cos(latRad);
        double A = Math.cos(latRad) * (lonRad - lonOriginRad);

        double M = a * ((1 - eccSquared / 4 - 3 * eccSquared * eccSquared / 64
                - 5 * eccSquared * eccSquared * eccSquared / 256) * latRad
                - (3 * eccSquared / 8 + 3 * eccSquared * eccSquared / 32
                + 45 * eccSquared * eccSquared * eccSquared / 1024) * Math.sin(2 * latRad)
                + (15 * eccSquared * eccSquared / 256
                + 45 * eccSquared * eccSquared * eccSquared / 1024) * Math.sin(4 * latRad)
                - (35 * eccSquared * eccSquared * eccSquared / 3072) * Math.sin(6 * latRad));

        double easting = (k0 * N * (A + (1 - T + C) * Math.pow(A, 3) / 6
                + (5 - 18 * T + T * T + 72 * C - 58 * eccPrimeSquared) * Math.pow(A, 5) / 120)
                + 500000.0);

        double northing = (k0 * (M + N * Math.tan(latRad) * (A * A / 2
                + (5 - T + 9 * C + 4 * C * C) * Math.pow(A, 4) / 24
                + (61 - 58 * T + T * T + 600 * C - 330 * eccPrimeSquared)
                * Math.pow(A, 6) / 720)));

        if (lat < 0) {
            northing += 10000000.0;
        }

        return new UTM(zone, hemisphere, easting, northing);
    }
}


