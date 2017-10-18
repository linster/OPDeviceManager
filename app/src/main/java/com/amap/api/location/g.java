package com.amap.api.location;

public class g {
    private double iI;
    private double iJ;

    public g(double d, double d2) {
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
        this.iI = d5;
        this.iJ = d6;
    }

    public double ir() {
        return this.iI;
    }

    public double is() {
        return this.iJ;
    }
}
