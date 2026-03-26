package com.bruno.calculadora;

public class Main {
    static void main() {
        Calculator calc = new Calculator();

        Seller seller = new Seller("bruno", 1100.0);

        // Prueba Escenario 1: Quiero 80 limpios
        System.out.println("Para 80 limpios: Pase de " + calc.calculateGamepassFromClean(80));

        // Prueba Escenario 2: Puse el pase a 115
        System.out.println("Si el pase es 115: Cobro " + calc.calculatePriceFromGamepass(115, seller) + " pesos");

        // Prueba Escenario 3: El cliente tiene 2000 pesos
        System.out.println("Con 2000 pesos: Le doy un pase de " + calc.calculateGamepassFromPrice(2000, seller));
    }

    }

