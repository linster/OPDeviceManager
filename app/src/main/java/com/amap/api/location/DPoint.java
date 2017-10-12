package com.amap.api.location;

public class DPoint {
    private double a;
    private double b;

    public DPoint(double d, double d2) {
        double d3 = 180.0d;
        double d4 = 90.0d;
        double d5 = -180.0d;
        double d6 = -90.0d;
        if (d2 <= 180.0d) {
            d3 = d2;
        }
        if (d3 >= -180.0d) {
            d5 = d3;
        }
        if (d <= 90.0d) {
            d4 = d;
        }
        if (d4 >= -90.0d) {
            d6 = d4;
        }
        this.a = d5;
        this.b = d6;
    }

    public double getLongitude() {
        return this.a;
    }

    public double getLatitude() {
        return this.b;
    }
}
