package com.bruno.calculadora;

public class Calculator {

    // ESCENARIO 1: El cliente ingresa cuántos Robux LIMPIOS quiere (Llegan)

    public int calculateGamepassFromClean(int cleanRobux) {
        double gamepassRobux = cleanRobux / 0.7;
        return (int) Math.ceil(gamepassRobux);

    }

    public int calculatePriceFromClean(int cleanRobux, Seller seller) {
        int gamepassPrice = calculateGamepassFromClean(cleanRobux);
        double sellerPrice = seller.getPricePer80Robux();
        double exactPrice = ( gamepassPrice * sellerPrice ) / 80.0;
        return (int) Math.round(exactPrice);
    }

}
