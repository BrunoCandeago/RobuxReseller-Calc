package com.bruno.robuxresellercalc;

public class Seller {
    private String name;
    private double pricePer80Robux;

    public Seller(String name, double pricePer80Robux) {
        this.name = name;
        this.pricePer80Robux = pricePer80Robux;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPricePer80Robux() {
        return pricePer80Robux;
    }

    public void setPricePer80Robux(double pricePer80Robux) {
        this.pricePer80Robux = pricePer80Robux;
    }

    @Override
    public String toString() {
        return String.format("%s ($%.2f)", name, pricePer80Robux);
    }
}
