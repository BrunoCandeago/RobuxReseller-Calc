package com.bruno.robuxresellercalc;

import com.formdev.flatlaf.themes.FlatMacDarkLaf;
import javax.swing.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class Main {
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(new FlatMacDarkLaf());
        } catch (Exception ex) {
            System.err.println("Failed to initialize LaF");
        }

        File configFile = new File("config.properties");

        if (!configFile.exists()) {
            try (FileWriter writer = new FileWriter(configFile)) {
                writer.write("seller1.name=Base\n");
                writer.write("seller1.pricePer80Robux=1100.0\n");
            } catch (IOException e) {
                JOptionPane.showMessageDialog(null, "Error creating config file", "Critical Error", JOptionPane.ERROR_MESSAGE);
                System.exit(1);
            }
        }

        List<Seller> sellerList = loadSellers(configFile);

        SwingUtilities.invokeLater(() -> {
            MainFrame mainFrame = new MainFrame(sellerList);
            mainFrame.setVisible(true);
        });
    }

    private static List<Seller> loadSellers(File file) {
        List<Seller> list = new ArrayList<>();
        Properties props = new Properties();

        try (FileInputStream is = new FileInputStream(file)) {
            props.load(is);

            for (String key : props.stringPropertyNames()) {
                if (key.endsWith(".name")) {
                    String prefix = key.substring(0, key.lastIndexOf("."));
                    String name = props.getProperty(prefix + ".name");
                    String priceStr = props.getProperty(prefix + ".pricePer80Robux");

                    if (name != null && priceStr != null) {
                        try {
                            double price = Double.parseDouble(priceStr.replace(",", "."));
                            list.add(new Seller(name, price));
                        } catch (NumberFormatException e) {
                            System.err.println("Invalid price for: " + name);
                        }
                    }
                }
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Could not read config.properties", "Error", JOptionPane.ERROR_MESSAGE);
        }
        return list;
    }
}