package com.bruno.calculadora;

public class Calculator {

    // ESCENARIO 1: El cliente ingresa cuántos Robux LIMPIOS quiere (Llegan)

    public long calculateGamepassFromClean(long cleanRobux) {
        double gamepassRobux = cleanRobux / 0.7;
        return (long) Math.ceil(gamepassRobux);

    }

    public long  calculatePriceFromClean(long cleanRobux, Seller seller) {
        long gamepassPrice = calculateGamepassFromClean(cleanRobux);
        double sellerPrice = seller.getPricePer80Robux();
        double exactPrice = ( gamepassPrice * sellerPrice ) / 80.0;
        return (long) Math.round(exactPrice);
    }

    // ESCENARIO 2: El cliente ingresa de cuánto es el PASE

    public long calculateCleanFromGamepass(long gamepassRobux) {
        return (long) (gamepassRobux * 0.7);
    }

    public long calculatePriceFromGamepass(long gamepassRobux, Seller seller) {
        double sellerPrice = seller.getPricePer80Robux();
        double exactPrice = ( gamepassRobux * sellerPrice) / 80.0;
        return (long) Math.round(exactPrice);
    }

    // ESCENARIO 3: El cliente ingresa cuánta PLATA (Pesos) tiene

    public long calculateGamepassFromPrice(double priceInPesos, Seller seller) {
        double sellerPrice = seller.getPricePer80Robux();
        double exactPrice = ( priceInPesos * 80.0 ) / sellerPrice;
        return (long) Math.floor(exactPrice);
    }

    public long calculateCleanFromPrice(double priceInPesos, Seller seller) {
        long gamepassAmount = calculateGamepassFromPrice(priceInPesos, seller);
        return calculateCleanFromGamepass(gamepassAmount);
    }

}
