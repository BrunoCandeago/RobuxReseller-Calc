package com.bruno.calculadora;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class Main {
    static void main() {

        File archivoFisico = new File("config.properties");

        if (!archivoFisico.exists()) {
            System.out.println("No se encontro el archivo. Creando configuracion por defecto...");
            try {
                FileWriter escritor = new FileWriter(archivoFisico);
                escritor.write("seller1.name=Base\n");
                escritor.write("seller1.pricePer80Robux=1100.0\n");
                escritor.close();
            } catch (IOException e) {
                System.out.println("Error al escribir el archivo.");
                System.exit(1);
            }
        }

        Properties config = new Properties();
        List<Seller> listaVendedores = new ArrayList<>();

        try {
            FileInputStream archivo = new FileInputStream("config.properties");
            config.load(archivo);
            archivo.close();

            int i = 1;

            while (true) {
                String nombre = config.getProperty("seller" + i + ".name");
                String priceStr = config.getProperty("seller" + i + ".pricePer80Robux");

                if (nombre == null || priceStr == null) {
                    break;
                }

                double pricePer80Robux = Double.parseDouble(priceStr);
                Seller seller = new Seller(nombre, pricePer80Robux);

                listaVendedores.add(seller);

                i++;
            }

            System.out.println("Vendedores cargados con exito");

        } catch (IOException e) {
            System.out.println("Error al cargar el archivo de configuraciones");
            System.exit(1);
        }

        System.out.println("Vendedores disponibles:");
        for (Seller seller : listaVendedores) {
            System.out.println(seller.getName() + " " + seller.getPricePer80Robux());
        }


    }

    }

