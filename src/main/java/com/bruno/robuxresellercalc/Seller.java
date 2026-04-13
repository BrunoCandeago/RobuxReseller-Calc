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

    public String serializeTiers() {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<Long, Double> entry : tiers.entrySet()) {
            String limit = (entry.getKey() == Long.MAX_VALUE) ? "MAX" : String.valueOf(entry.getKey());

            sb.append(limit).append(":").append(entry.getValue()).append(",");
        }

        if (sb.length() > 0) {
            sb.deleteCharAt(sb.length() - 1);
        }
        return sb.toString();
    }

    public void loadTiersFromString(String tiersString) {
        this.tiers.clear();

        String[] parts = tiersString.split(",");
        for (String part : parts) {
            String[] pair = part.split(":");

            long limit = pair[0].equalsIgnoreCase("MAX") ? Long.MAX_VALUE : Long.parseLong(pair[0]);
            double price = Double.parseDouble(pair[1]);

            this.addTiers(limit, price);
        }
    }

    @Override
    public String toString() {
        return name;
    }
}
