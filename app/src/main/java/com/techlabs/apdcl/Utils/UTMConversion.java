package com.techlabs.apdcl.Utils;

import org.osmdroid.util.GeoPoint;

public class UTMConversion {

    public static GeoPoint convert(double easting, double northing) {

        if (isLatLon(easting, northing)) {
            return new GeoPoint(northing, easting);
        }

        int zone = 43;
        boolean isNorthern = true;

        double a = 6378137.0;
        double eccSquared = 0.00669438;
        double k0 = 0.9996;

        double eccPrimeSquared;
        double e1 = (1 - Math.sqrt(1 - eccSquared)) / (1 + Math.sqrt(1 - eccSquared));

        double x = easting - 500000.0;
        double y = northing;

        if (!isNorthern) {
            y -= 10000000.0;
        }

        eccPrimeSquared = eccSquared / (1 - eccSquared);

        double M = y / k0;

        double mu = M / (a * (1
                - eccSquared / 4
                - 3 * eccSquared * eccSquared / 64
                - 5 * eccSquared * eccSquared * eccSquared / 256));

        double phi1Rad = mu
                + (3 * e1 / 2 - 27 * Math.pow(e1, 3) / 32) * Math.sin(2 * mu)
                + (21 * e1 * e1 / 16 - 55 * Math.pow(e1, 4) / 32) * Math.sin(4 * mu)
                + (151 * Math.pow(e1, 3) / 96) * Math.sin(6 * mu)
                + (1097 * Math.pow(e1, 4) / 512) * Math.sin(8 * mu);

        double N1 = a / Math.sqrt(1 - eccSquared * Math.sin(phi1Rad) * Math.sin(phi1Rad));
        double T1 = Math.tan(phi1Rad) * Math.tan(phi1Rad);
        double C1 = eccPrimeSquared * Math.cos(phi1Rad) * Math.cos(phi1Rad);
        double R1 = a * (1 - eccSquared) / Math.pow(1 - eccSquared * Math.sin(phi1Rad) * Math.sin(phi1Rad), 1.5);

        double D = x / (N1 * k0);

        double lat = phi1Rad - (N1 * Math.tan(phi1Rad) / R1)
                * (D * D / 2
                - (5 + 3 * T1 + 10 * C1 - 4 * C1 * C1 - 9 * eccPrimeSquared)
                * Math.pow(D, 4) / 24
                + (61 + 90 * T1 + 298 * C1 + 45 * T1 * T1
                - 252 * eccPrimeSquared - 3 * C1 * C1)
                * Math.pow(D, 6) / 720);

        double lon = (D - (1 + 2 * T1 + C1) * Math.pow(D, 3) / 6
                + (5 - 2 * C1 + 28 * T1 - 3 * C1 * C1
                + 8 * eccPrimeSquared + 24 * T1 * T1)
                * Math.pow(D, 5) / 120) / Math.cos(phi1Rad);

        double lonOrigin = (zone - 1) * 6 - 180 + 3;

        double latitude = Math.toDegrees(lat);
        double longitude = lonOrigin + Math.toDegrees(lon);

        return new GeoPoint(latitude, longitude);
    }

    private static boolean isLatLon(double x, double y) {
        return x >= -180 && x <= 180 && y >= -90 && y <= 90;
    }

    /*public static GeoPoint convert(double easting, double northing) {

        int zone = 43;
        boolean isNorthern = true;

        double k0 = 0.9996;
        double a = 6378137.0;
        double eccSquared = 0.00669438;
        double eccPrimeSquared;
        double e1 = (1 - Math.sqrt(1 - eccSquared)) / (1 + Math.sqrt(1 - eccSquared));
        double N1, T1, C1, R1, D, M;
        double mu, phi1Rad;

        double x = easting - 500000.0;
        double y = northing;

        eccPrimeSquared = (eccSquared) / (1 - eccSquared);

        M = y / k0;
        mu = M / (a * (1 - eccSquared / 4 - 3 * eccSquared * eccSquared / 64 - 5 * eccSquared * eccSquared * eccSquared / 256));

        phi1Rad = mu +
                (3 * e1 / 2 - 27 * Math.pow(e1, 3) / 32) * Math.sin(2 * mu)
                + (21 * e1 * e1 / 16 - 55 * Math.pow(e1, 4) / 32) * Math.sin(4 * mu)
                + (151 * Math.pow(e1, 3) / 96) * Math.sin(6 * mu);

        N1 = a / Math.sqrt(1 - eccSquared * Math.sin(phi1Rad) * Math.sin(phi1Rad));
        T1 = Math.tan(phi1Rad) * Math.tan(phi1Rad);
        C1 = eccPrimeSquared * Math.cos(phi1Rad) * Math.cos(phi1Rad);
        R1 = a * (1 - eccSquared) / Math.pow(1 - eccSquared * Math.sin(phi1Rad) * Math.sin(phi1Rad), 1.5);
        D = x / (N1 * k0);

        double lat = phi1Rad - (N1 * Math.tan(phi1Rad) / R1)
                * (D * D / 2 -
                (5 + 3 * T1 + 10 * C1 - 4 * C1 * C1 - 9 * eccPrimeSquared) * Math.pow(D, 4) / 24);

        double lon = (D - (1 + 2 * T1 + C1) * Math.pow(D, 3) / 6)
                / Math.cos(phi1Rad);

        double lonOrigin = (zone - 1) * 6 - 180 + 3;

        double latitude = Math.toDegrees(lat);
        double longitude = lonOrigin + Math.toDegrees(lon);

        return new GeoPoint(latitude, longitude);
    }*/

}
