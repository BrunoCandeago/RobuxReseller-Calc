package com.bruno.calculadora;

public class Seller {
    private String name;
    private Double pricePer80Robux; // Precio por 80 robux

    public Seller(String name, Double pricePer80Robux) {
        this.name = name;
        this.pricePer80Robux = pricePer80Robux;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPricePer80Robux() {
        return pricePer80Robux;
    }

    public void setPricePer80Robux(Double pricePer80Robux) {
        this.pricePer80Robux = pricePer80Robux;
    }
}
