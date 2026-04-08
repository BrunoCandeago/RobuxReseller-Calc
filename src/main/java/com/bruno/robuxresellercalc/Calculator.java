package com.bruno.robuxresellercalc;

public class Calculator {
    private static final double ROBLOX_TAX_RATE = 0.7;
    private static final double BASE_ROBUX_AMOUNT = 80.0;

    public long calculateGamepassFromClean(long cleanRobux) {
        double gamepassRobux = cleanRobux / ROBLOX_TAX_RATE;
        return (long) Math.ceil(gamepassRobux);

    }

    public long  calculatePriceFromClean(long cleanRobux, Seller seller) {
        long gamepassPrice = calculateGamepassFromClean(cleanRobux);
        double sellerPrice = seller.getPricePer80Robux();
        double exactPrice = ( gamepassPrice * sellerPrice ) / BASE_ROBUX_AMOUNT;
        return (long) Math.round(exactPrice);
    }


    public long calculateCleanFromGamepass(long gamepassRobux) {
        return (long) (gamepassRobux * ROBLOX_TAX_RATE);
    }

    public long calculatePriceFromGamepass(long gamepassRobux, Seller seller) {
        double sellerPrice = seller.getPricePer80Robux();
        double exactPrice = ( gamepassRobux * sellerPrice) / BASE_ROBUX_AMOUNT;
        return (long) Math.round(exactPrice);
    }


    public long calculateGamepassFromPrice(double price, Seller seller) {
        double sellerPrice = seller.getPricePer80Robux();
        double exactPrice = ( price * BASE_ROBUX_AMOUNT ) / sellerPrice;
        return (long) Math.floor(exactPrice);
    }

    public long calculateCleanFromPrice(double price, Seller seller) {
        long gamepassAmount = calculateGamepassFromPrice(price, seller);
        return calculateCleanFromGamepass(gamepassAmount);
    }

}
