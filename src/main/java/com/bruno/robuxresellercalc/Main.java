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


        ConfigManager.createDefaultConfig();
        List<Seller> sellerList = ConfigManager.loadSellers();

        SwingUtilities.invokeLater(() -> {
            MainFrame mainFrame = new MainFrame(sellerList);
            mainFrame.setVisible(true);
        });
    }
}