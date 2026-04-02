package com.bruno.calculadora;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.FileWriter;

public class AddSellerDialog extends javax.swing.JDialog {

    private static final String CONFIG_FILE = "config.properties";

    private JTextField fieldName;
    private JTextField fieldPrice;
    private JButton btnSave;

    private int sellerId;

    private boolean saved = false;

    public AddSellerDialog(java.awt.Frame parent, boolean modal, int newId) {
        super(parent, modal);
        this.sellerId = newId;
        setTitle("Add Seller");
        setSize(300, 200);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(parent);

        initUI();
        initListeners();

    }


    private void initUI() {
        setLayout(new BorderLayout(10, 10));

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3,2,10,10));
        JLabel labelName = new JLabel("SellerName:");
        fieldName = new JTextField();

        JLabel labelPrice = new JLabel("PricePer80Robux:");
        fieldPrice = new JTextField();
        ((javax.swing.text.AbstractDocument) fieldPrice.getDocument()).setDocumentFilter(new NumericLimitFilter(10));

        btnSave = new JButton("Save changes");

        panel.add(labelName);
        panel.add(fieldName);
        panel.add(labelPrice);
        panel.add(fieldPrice);
        panel.add(new JLabel(""));
        panel.add(btnSave);

        panel.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));
        add(panel);
    }

    private void initListeners() {
        ActionListener actionListenerSave = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {saveSeller();}
        };

        btnSave.addActionListener(actionListenerSave);
    }

    private void saveSeller() {
        String name = getSellerName();
        String price = getSellerPrice();

        String nameLine = "seller" + sellerId + ".name=" + name;
        String priceLine = "seller" + sellerId + ".pricePer80Robux=" + price;

        try (FileWriter fw = new FileWriter(CONFIG_FILE, true);
            BufferedWriter bw = new BufferedWriter(fw)) {

            bw.newLine();
            bw.write(nameLine);
            bw.newLine();
            bw.write(priceLine);

            saved = true;
            dispose();
        } catch (Exception e) {
            System.out.println("Error saving the file");
            e.printStackTrace();
        }
    }

    public boolean isSaved() {return saved;}
    public String getSellerName() {return fieldName.getText();}
    public String getSellerPrice() {return fieldPrice.getText();}
}
