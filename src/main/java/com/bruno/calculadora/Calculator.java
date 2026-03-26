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

    // ESCENARIO 2: El cliente ingresa de cuánto es el PASE

    public int calculateCleanFromGamepass(int gamepassRobux) {
        return (int) (gamepassRobux * 0.7);
    }

    public int calculatePriceFromGamepass(int gamepassRobux, Seller seller) {
        double sellerPrice = seller.getPricePer80Robux();
        double exactPrice = ( gamepassRobux * sellerPrice) / 80.0;
        return (int) Math.round(exactPrice);
    }

    // ESCENARIO 3: El cliente ingresa cuánta PLATA (Pesos) tiene

    public int calculateGamepassFromPrice(double priceInPesos, Seller seller) {
        double sellerPrice = seller.getPricePer80Robux();
        double exactPrice = ( priceInPesos * 80.0 ) / sellerPrice;
        return (int) Math.floor(exactPrice);
    }

}
