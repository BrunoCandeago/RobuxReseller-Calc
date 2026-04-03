package com.bruno.calculadora;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class MainFrame extends JFrame {

    private JTextField fieldClean;
    private JTextField fieldGamepass;
    private JTextField fieldPrice;
    private JComboBox<Seller> sellerBox;
    private JButton btnAddSeller;
    private JButton btnCopyClean;
    private JButton btnCopyGamepass;
    private JButton btnCopyPrice;
    private List<Seller> sellerList;

    private boolean isCalculating = false;

    public MainFrame(List<Seller> sellerList) {
        this.sellerList = sellerList;

        setTitle("RobuxReseller Calc");
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        initUI();
        initListeners();

        pack();
        setLocationRelativeTo(null);
    }

    private void initUI() {
        setLayout(new BorderLayout(10, 10));

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(4, 2, 10, 10));

        JLabel labelClean = new JLabel("Clean Robux: ");
        fieldClean = new JTextField(10);
        ((javax.swing.text.AbstractDocument) fieldClean.getDocument()).setDocumentFilter(new NumericLimitFilter(10, false));

        btnCopyClean = new JButton("Copy");

        JPanel wrapClean = new JPanel(new BorderLayout(5, 0));
        wrapClean.add(fieldClean, BorderLayout.CENTER);
        wrapClean.add(btnCopyClean, BorderLayout.EAST);

        JLabel labelGamepass = new JLabel("Gamepass Robux: ");
        fieldGamepass = new JTextField(10);
        ((javax.swing.text.AbstractDocument) fieldGamepass.getDocument()).setDocumentFilter(new NumericLimitFilter(10, false));

        btnCopyGamepass = new JButton("Copy");

        JPanel wrapGamepass = new JPanel(new BorderLayout(5, 0));
        wrapGamepass.add(fieldGamepass, BorderLayout.CENTER);
        wrapGamepass.add(btnCopyGamepass, BorderLayout.EAST);

        JLabel labelPrice = new JLabel("Price: ");
        fieldPrice = new JTextField(10);
        ((javax.swing.text.AbstractDocument) fieldPrice.getDocument()).setDocumentFilter(new NumericLimitFilter(10, false));

        btnCopyPrice = new JButton("Copy");

        JPanel wrapPrice = new JPanel(new BorderLayout(5, 0));
        wrapPrice.add(fieldPrice, BorderLayout.CENTER);
        wrapPrice.add(btnCopyPrice, BorderLayout.EAST);

        JLabel labelSeller = new JLabel("Seller: ");

        sellerBox = new JComboBox<>();
        for (Seller s : sellerList) {
            sellerBox.addItem(s);
        }

        btnAddSeller = new JButton("Add Seller");

        JPanel wrapSeller = new JPanel(new BorderLayout(5, 0));
        wrapSeller.add(sellerBox, BorderLayout.CENTER);
        wrapSeller.add(btnAddSeller, BorderLayout.EAST);

        panel.add(labelClean);
        panel.add(wrapClean);

        panel.add(labelGamepass);
        panel.add(wrapGamepass);

        panel.add(labelPrice);
        panel.add(wrapPrice);

        panel.add(labelSeller);
        panel.add(wrapSeller);

        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        add(panel, BorderLayout.NORTH);
    }

    private void initListeners() {
        DocumentListener documentListenerClean = new DocumentListener() {
            public void changedUpdate(DocumentEvent e) {}
            public void insertUpdate(DocumentEvent e) { updateFromClean(); }
            public void removeUpdate(DocumentEvent e) {
                updateFromClean();
            }
        };

        DocumentListener documentListenerGamepass = new DocumentListener() {
            public void changedUpdate(DocumentEvent e) {}
            public void insertUpdate(DocumentEvent e) {
                updateFromGamepass();
            }
            public void removeUpdate(DocumentEvent e) {
                updateFromGamepass();
            }
        };

        DocumentListener documentListenerPrice = new DocumentListener() {
            public void changedUpdate(DocumentEvent e) {}
            public void insertUpdate(DocumentEvent e) {
                updateFromPrice();
            }
            public void removeUpdate(DocumentEvent e) {
                updateFromPrice();
            }
        };

        ActionListener actionListenerBox = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Seller actualSeller = (Seller) sellerBox.getSelectedItem();

                if (actualSeller != null) {

                }

                updateFromClean();
            }
        };

        ActionListener actionListenerSeller = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {addSeller();}
        };

        ActionListener actionListenerCopyAll = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == btnCopyClean) {
                    copyToClipboard(fieldClean.getText());
                } else if (e.getSource() == btnCopyGamepass) {
                    copyToClipboard(fieldGamepass.getText());
                } else if (e.getSource() == btnCopyPrice) {
                    copyToClipboard(fieldPrice.getText());
                }
            }
        };

        fieldClean.getDocument().addDocumentListener(documentListenerClean);
        fieldGamepass.getDocument().addDocumentListener(documentListenerGamepass);
        fieldPrice.getDocument().addDocumentListener(documentListenerPrice);
        sellerBox.addActionListener(actionListenerBox);
        btnAddSeller.addActionListener(actionListenerSeller);
        btnCopyClean.addActionListener(actionListenerCopyAll);
        btnCopyGamepass.addActionListener(actionListenerCopyAll);
        btnCopyPrice.addActionListener(actionListenerCopyAll);

    }

    private void updateFromClean() {
        if (isCalculating) {
            return;
        }

        SwingUtilities.invokeLater(() -> {
        try {

            isCalculating = true;

            long textClean = Long.parseLong(fieldClean.getText());
            Calculator calculator = new Calculator();
            Seller actualSeller = (Seller) sellerBox.getSelectedItem();

            long resultadoGamepass = calculator.calculateGamepassFromClean(textClean);
            long resultadoPrecio = calculator.calculatePriceFromClean(textClean, actualSeller);

            fieldGamepass.setText(String.valueOf(resultadoGamepass));
            fieldPrice.setText(String.valueOf(resultadoPrecio));

        } catch (NumberFormatException ex) {
            fieldGamepass.setText("");
            fieldPrice.setText("");
        } finally {
            isCalculating = false;
        }
    });
        }

    private void updateFromGamepass() {
        if (isCalculating) {
            return;
        }

        SwingUtilities.invokeLater(() -> {

            try {

                isCalculating = true;

                long textGamepass = Long.parseLong(fieldGamepass.getText());
                Calculator calculator = new Calculator();
                Seller actualSeller = (Seller) sellerBox.getSelectedItem();

                long resultadoClean = calculator.calculateCleanFromGamepass(textGamepass);
                long resultadoPrecio = calculator.calculatePriceFromGamepass(textGamepass, actualSeller);

                fieldClean.setText(String.valueOf(resultadoClean));
                fieldPrice.setText(String.valueOf(resultadoPrecio));

            } catch (NumberFormatException ex) {
                fieldClean.setText("");
                fieldPrice.setText("");
            } finally {
                isCalculating = false;
            }
        });
    }

    private void updateFromPrice() {
        if (isCalculating) {
            return;
        }

        SwingUtilities.invokeLater(() -> {
            try {

                isCalculating = true;

                long textPrice = Long.parseLong(fieldPrice.getText());
                Calculator calculator = new Calculator();
                Seller actualSeller = (Seller) sellerBox.getSelectedItem();

                long resultadoGamepass = calculator.calculateGamepassFromPrice(textPrice, actualSeller);
                long resultadoClean = calculator.calculateCleanFromPrice(textPrice, actualSeller);

                fieldGamepass.setText(String.valueOf(resultadoGamepass));
                fieldClean.setText(String.valueOf(resultadoClean));

            } catch (NumberFormatException ex) {
                fieldGamepass.setText("");
                fieldClean.setText("");
            } finally {
                isCalculating = false;
            }
        });
    }

    private void addSeller() {
        AddSellerDialog sellerDialog = new AddSellerDialog(this, true, sellerList.size() + 1);
        sellerDialog.setVisible(true);

        if (sellerDialog.isSaved()) {
            String newName = sellerDialog.getSellerName();
            String newPrice = sellerDialog.getSellerPrice();

            double newPriceDouble = Double.parseDouble(newPrice.replace(",","."));

            Seller newSeller = new Seller(newName, newPriceDouble);

            sellerList.add(newSeller);
            sellerBox.addItem(newSeller);
        }
    }

    private void copyToClipboard(String text) {
        if (text != null &&  !text.isEmpty()) {
            StringSelection stringSelection = new StringSelection(text);
            Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
            clipboard.setContents(stringSelection, null);
        }
    }

}
