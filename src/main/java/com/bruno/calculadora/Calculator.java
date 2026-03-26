package com.bruno.calculadora;

public class Calculator {

    public int calculateGamepassFromClean(int cleanRobux) {
        double gamepassRobux = cleanRobux / 0.7;
        return (int) Math.ceil(gamepassRobux);

    }

}
