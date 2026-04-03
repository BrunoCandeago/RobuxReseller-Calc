package com.bruno.robuxresellercalc;

import javax.swing.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import com.formdev.flatlaf.themes.FlatMacDarkLaf;

public class Main {
    public static void main(String[] args) {

        try {
            UIManager.setLookAndFeel(new FlatMacDarkLaf());
        } catch (Exception ex) {
            System.err.println("Failed to initialize LaF");
        }

        File configFile = new File("config.properties");

        if (!configFile.exists()) {
            System.out.println("File not found. Creating default configuration...");
            try {
                FileWriter writer = new FileWriter(configFile);
                writer.write("seller1.name=Base\n");
                writer.write("seller1.pricePer80Robux=1100.0\n");
                writer.close();
            } catch (IOException e) {
                System.out.println("Error writing the file.");
                System.exit(1);
            }
        }

        Properties config = new Properties();
        List<Seller> sellerList = new ArrayList<>();

        try {
            FileInputStream inputStream = new FileInputStream("config.properties");
            config.load(inputStream);
            inputStream.close();

            int i = 1;

            while (true) {
                String name = config.getProperty("seller" + i + ".name");
                String priceStr = config.getProperty("seller" + i + ".pricePer80Robux");

                if (name == null || priceStr == null) {
                    break;
                }

                double pricePer80Robux = Double.parseDouble(priceStr);
                Seller seller = new Seller(name, pricePer80Robux);

                sellerList.add(seller);

                i++;
            }

        } catch (IOException e) {
            System.out.println("Error loading configuration file.");
            System.exit(1);
        }

    MainFrame mainFrame = new MainFrame(sellerList);

    mainFrame.setVisible(true);


    }

    }

