package com.bruno.robuxresellercalc;

public class Calculator {
    private static final double ROBLOX_TAX_RATE = 0.7;

    public long calculateGamepassFromClean(long cleanRobux) {
        double gamepassRobux = cleanRobux / ROBLOX_TAX_RATE;
        return (long) Math.ceil(gamepassRobux);

    }

    public long  calculatePriceFromClean(long cleanRobux, Seller seller) {
        long gamepassPrice = calculateGamepassFromClean(cleanRobux);
        double sellerPrice = seller.getUnitPrice(gamepassPrice);
        double exactPrice = gamepassPrice * sellerPrice;
        return (long) Math.round(exactPrice);
    }


    public long calculateCleanFromGamepass(long gamepassRobux) {
        return (long) (gamepassRobux * ROBLOX_TAX_RATE);
    }

    public long calculatePriceFromGamepass(long gamepassRobux, Seller seller) {
        double sellerPrice = seller.getUnitPrice(gamepassRobux);
        double exactPrice = gamepassRobux * sellerPrice;
        return (long) Math.round(exactPrice);
    }


    public long calculateGamepassFromPrice(double price, Seller seller) {
        return seller.getMaxRobuxFromPrice(price);
    }

    public long calculateCleanFromPrice(double price, Seller seller) {
        long gamepassAmount = calculateGamepassFromPrice(price, seller);
        return calculateCleanFromGamepass(gamepassAmount);
    }

}
