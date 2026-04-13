package com.bruno.robuxresellercalc;

import javax.swing.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class ConfigManager {
    public static File getConfigFile() {
        String userHome = System.getProperty("user.home");
        File appFolder = new File(userHome, ".robuxcalc");

        if (!appFolder.exists()) {
            appFolder.mkdir();
        }
        return new File(appFolder, "config.properties");
    }

    public static void createDefaultConfig() {
        File configFile = getConfigFile();

        if (!configFile.exists()) {
            try (FileWriter writer = new FileWriter(configFile)) {
                writer.write("seller1.name=Base\n");
                writer.write("seller1.tiers=MAX:13.75\n");
            } catch (IOException e) {
                JOptionPane.showMessageDialog(null, "Error creating config file", "Critical Error", JOptionPane.ERROR_MESSAGE);
                System.exit(1);
            }
        }
    }

    public static List<Seller> loadSellers() {
        List<Seller> list = new ArrayList<>();
        Properties props = new Properties();
        File file = getConfigFile();

        try (FileInputStream is = new FileInputStream(file)) {
            props.load(is);

            for (String key : props.stringPropertyNames()) {
                if (key.endsWith(".name")) {
                    String prefix = key.substring(0, key.lastIndexOf("."));
                    String name = props.getProperty(prefix + ".name");

                    String tiersStr = props.getProperty(prefix + ".tiers");

                    if (name != null && tiersStr != null) {
                        try {
                            long id = Long.parseLong(prefix.replace("seller",""));

                            Seller seller = new Seller(id, name);

                            seller.loadTiersFromString(tiersStr);

                            list.add(seller);
                        } catch (NumberFormatException e) {
                            System.err.println("Invalid tiers for: " + name);
                        }
                    }
                }
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Could not read config.properties", "Error", JOptionPane.ERROR_MESSAGE);
        }
        list.sort(java.util.Comparator.comparingLong(Seller::getId));
        return list;
    }

    public static void saveSellers(List<Seller> sellerList) {
        try (FileWriter writer = new FileWriter(getConfigFile(), false)) {
            for (Seller seller: sellerList) {
                writer.write("seller" + seller.getId() + ".name=" + seller.getName() + "\n");
                writer.write("seller" + seller.getId() + ".tiers=" + seller.serializeTiers() + "\n");
            }
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Could not save config.properties", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}