package com.bruno.robuxresellercalc;

public class Seller {
    private long id;
    private String name;
    private double pricePer80Robux;

    public Seller(long id, String name, double pricePer80Robux) {
        this.id = id;
        this.name = name;
        this.pricePer80Robux = pricePer80Robux;

    }

    public long getId() {return id;}

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
