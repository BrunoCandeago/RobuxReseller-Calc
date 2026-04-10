package com.bruno.robuxresellercalc;

import java.util.Map;
import java.util.TreeMap;

public class Seller {
    private long id;
    private String name;
    private TreeMap<Long,Double> tiers;

    public Seller(long id, String name) {
        this.id = id;
        this.name = name;
        this.tiers = new TreeMap<>();

    }

    public long getId() {return id;}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public void addTiers(long maxRobux, double price) {
        tiers.put(maxRobux,price);
    }

    public double getUnitPrice(long requestedRobux) {
        return tiers.ceilingEntry(requestedRobux).getValue();
    }

    public long getMaxRobuxFromPrice(double price) {
        for (Map.Entry<Long, Double> rango : tiers.entrySet()) {
            long possibleRobux = (long) Math.floor(price / rango.getValue());

            if (possibleRobux <= rango.getKey()) return possibleRobux;
        }
        return 0;
    }

    @Override
    public String toString() {
        return name;
    }
}
